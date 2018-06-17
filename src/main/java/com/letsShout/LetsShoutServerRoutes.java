package com.letsShout;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import com.letsShout.client.TwitterClientCache;
import com.letsShout.model.MessageRegistry;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * Shouter service routes
 */
public class LetsShoutServerRoutes extends AllDirectives {

    private final LoggingAdapter log;

    private final ActorRef actorRef;
    private final TwitterClientCache twitterClientCache;

    // Required by the `ask` (?) method below
    Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS)); // usually we'd obtain the timeout from the system's configuration

    public LetsShoutServerRoutes(ActorSystem system, ActorRef actorRef, TwitterClientCache twitterClientCache) {
        log = Logging.getLogger(system, this);

        this.actorRef = actorRef;
        this.twitterClientCache = twitterClientCache;
    }

    /**
     * This method creates one route (of possibly many more that will be part of your Web App)
     */
    public Route routes() {
        return route(pathPrefix("letsShout", () ->
                route(
                        path(PathMatchers.segments(2), segments ->
                                route(
                                        getSearchTweets(segments.get(0), segments.get(1))
                                )
                        )
                )
        ));
    }

    private Route getSearchTweets(String username, String n) {
        return get(() -> {
            CompletionStage<Optional<List<String>>> completionStage = PatternsCS
                    .ask(actorRef, new MessageRegistry.SearchTweets(username, n, twitterClientCache), timeout)
                    .thenApply(obj -> (Optional<List<String>>) obj);
            return onSuccess(() -> completionStage,
                    searchTweets -> complete(
                            StatusCodes.OK,
                            searchTweets.get(),
                            Jackson.marshaller()));
        });
    }
}

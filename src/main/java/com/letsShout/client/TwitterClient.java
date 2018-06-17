package com.letsShout.client;

import twitter4j.*;

/**
 * Twitter client
 */
public class TwitterClient {

    private final Twitter twitter;

    /**
     * Twitter client default constructor
     */
    public TwitterClient() {
        twitter = new TwitterFactory().getInstance();
    }

    /** Retrieves a user timeline given its username
     * @param username the user username
     * @return a user timeline
     * @throws TwitterException
     */
    public ResponseList<Status> getUserTimeline(String username) throws TwitterException {
        return twitter.getUserTimeline(username);
    }

    /**
     * Checks if the client is authorized to access twitter
     *
     * @return a boolean with the result of the authorization
     */
    public boolean isAuthorized() {
        return twitter.getAuthorization().isEnabled();
    }
}
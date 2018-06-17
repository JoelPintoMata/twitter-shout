# Lets Shout Service

## Run
$ mvn compile exec:java -Dtwitter4j.oauth.consumerKey=<comsumer_key> -Dtwitter4j.oauth.consumerSecret=<consumer_secret> -Dtwitter4j.oauth.accessToken=<access_token> -Dtwitter4j.oauth.accessTokenSecret=<access_token_secrete> -Dexec.mainClass="<main_class>"

## Test
$ mvn test

## Usage
http://<hostname>:<port>/letsShout/<username>/<n>
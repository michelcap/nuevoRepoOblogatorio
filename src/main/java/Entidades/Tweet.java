package Entidades;

public class Tweet implements Comparable<Tweet>{

    private long id;
    private String content;
    private String  source;
    private boolean isRetweet;
    private User usuarioDelTweet;

    // getters and setters...

    public Tweet(long id) {
        this.id = id;
    }


    @Override
    public int compareTo(Tweet otherTweet) {
        return Long.compare(this.id, otherTweet.id);
    }
}

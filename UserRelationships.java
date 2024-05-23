public interface UserRelationships {
    public User friend(String f);
    public User follow(String f);
    public User unfollow(String f);
    public User unfriend(String f);
    public boolean isFriend(User u);
    public boolean isFollower(User u);
}

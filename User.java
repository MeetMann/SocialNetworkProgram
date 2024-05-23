import java.util.*;

/* User.java
 *
 * This class represents a user in a social network. The code uses a static
 * HashMap to keep track of all users. The HashMap is keyed by the user's name
 * and can be searched using the find method.
 *
 * Users can:
 *  - friend another user
 * - unfriend another user
 * - leave the social network
 * - check if they are friends with another user
 */
public class User extends UserServices implements UserRelationships {
  private String name;
  private HashMap<String,User> adj = new HashMap<String,User>();
  private HashMap<String,User> flw = new HashMap<String,User>();

  /* Constructor for the User class. The constructor takes a String, nm, which
   * is the name of the user. The constructor adds the user to the static
   * HashMap. Warning: if a user with the same name already exists, the
   * constructor will not add the new user to the HashMap.
   */
  public User( String nm ) {
    name = nm;
    if (!users.containsKey(name)) {
      users.put(name, this);
    }
  }

  /* setName
   * Sets the name of the user.
   * @param nm The new name for the user.
   */
  public void setName(String nm) {
    this.name = nm;
  }

  /* getName
   * Retrieves the name of the user.
   * @return The name of the user.
   */
  public String getName() {
    return this.name;
  }

  /* getAdjValues
   * Retrieves the collection of friends.
   * @return The collection of friends.
   */
  public Collection<User> getAdjValues() {
    return this.adj.values();
  }

  /* getFlwValues
   * Retrieves the collection this user is following
   * @return The collection this user is following.
   */
  public Collection<User> getFlwValues() {
    return this.flw.values();
  }

  /* getAdjAt
   * Retrieves the friend with the specified name.
   * @param nm The name of the friend to retrieve.
   * @return The friend with the specified name.
   */
  public User getAdjAt(String nm) {
    return this.adj.get(nm);
  }

  /* getFlwAt
   * Retrieves the followee with the specified name.
   * @param nm The name of the followee to retrieve.
   * @return The followee with the specified name.
   */
  public User getFlwAt(String nm) {
    return this.flw.get(nm);
  }

  /* removeAdjAt
   * Removes the friend with the specified name.
   * @param nm The name of the friend to remove.
   * @return The former friend with the specified name.
   */
  public User removeAdjAt(String nm) {
    return this.adj.remove(nm);
  }

  /* removeFlwAt
   * Removes the followee with the specified name.
   * @param nm The name of the followee to remove.
   * @return The former followee with the specified name.
   */
  public User removeFlwAt(String nm) {
    return this.flw.remove(nm);
  }

  /* getAdjSize
   * Retrieves the number of friends.
   * @return The number of friends.
   */
  public int getAdjSize() {
    return this.adj.size();
  }

  /* getFlwSize
   * Retrieves the number following.
   * @return The number of users the user is following.
   */
  public int getFlwSize() {
    return this.flw.size();
  }

  /* friend
   * Given a String, f, this method will friend the user with that name. The
   * method returns the User that was friended. Friending adds the friendship to
   * adj and to the other user's adj. Friending a user that is already a friend
   * does not change the friendship.
   *
   */
  public User friend( String f ) {
    User u = users.get( f );
    if (flw.containsKey( u.name )) {
      flw.remove( u.name );
    }
    if (u.flw.containsKey( this.name )) {
      u.flw.remove( this.name );
    }
    adj.put( u.name, u );
    u.adj.put( name, this );
    return u;
  }

  /* follow
   * Given a String, f, this method will follow the user with that name. The
   * method returns the User that was followed as long as they are not already friends,
   * otherwise, it just returns null. Following adds the followed user to adj. Following
   * a user that is already a friend does not change the friendship.
   *
   */
  public User follow( String f ) {
    User u = users.get( f );
    if (!this.isFriend(u)) {
      flw.put(u.name, u);
      return u;
    }
    return null;
  }

  /* unfollow
   * Given a String, f, this method will unfollow the user with that name. The
   * method returns the User that was unfollowed. Unfollowing removes the
   * follower from flw.
   */
  public User unfollow( String f ) {
    User u = users.get( f );
    flw.remove( u.name );
    return u;
  }

  /* unfriend
   * Given a String, f, this method will unfriend the user with that name. The
   * method returns the User that was unfriended. Unfriending removes the
   * friendship from adj, from the other user's adj, and from flw.
   */
  public User unfriend( String f ) {
    User u = users.get( f );
    adj.remove( u.name );
    flw.remove( u.name );
    u.adj.remove( this.name );
    return u;
  }

  /* isFriend
   * Given a User, u, this method returns true if u is a friend of this user and
   * false otherwise.
   */
  public boolean isFriend( User u ) {
    return adj.containsKey( u.name );
  }

  /* isFollower
   * Given a User, u, this method returns true if this user is a follower of u and
   * false otherwise.
   */
  public boolean isFollower( User u ) {
    return flw.containsKey( u.name );
  }
}

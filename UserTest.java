import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class UserTest {
  private static String[] friendNames = {"Alice", "Bob", "Carol", "Dave", "Eve", "Frank", "Grace"};

  // clear the static users HashMap before each test
  @AfterEach
  void clearUsers() {
    User.users.clear();
  }

  // Test constructing a user adds them to the HashMap
  @Test
  void testConstructor() {
    User u = new User(friendNames[0]);
    assertEquals(friendNames[0], u.getName(), "Incorrect name");
    assertEquals(1, User.users.size(), "Incorrect size");
    assertEquals(u, User.users.get(friendNames[0]), "User not in HashMap");
  }

  /* Constructing two users with the same name should
   * add the first user to the HashMap but not the second.
   */
  @Test
  void testConstructorSameName() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[0]);
    assertEquals(friendNames[0], u.getName(), "Incorrect name");
    assertEquals(friendNames[0], v.getName(), "Incorrect name");
    assertEquals(1, User.users.size(), "Incorrect size");
    assertEquals(u, User.users.get(friendNames[0]), "User not in HashMap");
  }

  // Test constructing two users with different names adds both to the HashMap
  @Test
  void testConstructorDifferentName() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    assertEquals(friendNames[0], u.getName(), "Incorrect name for u");
    assertEquals(friendNames[1], v.getName(), "Incorrect name for v");
    assertEquals(2, User.users.size(), "Incorrect size");
    assertEquals(u, User.users.get(friendNames[0]), "User u not in HashMap");
    assertEquals(v, User.users.get(friendNames[1]), "User v not in HashMap");
  }

  // Test setName updates the name of the user
  @Test
  void testSetName() {
    User u = new User(friendNames[0]);
    String newName = "John";
    u.setName(newName);
    assertEquals(newName, u.getName(), "Name not updated correctly");
  }

  // Test getAdjValues returns the collection of friends
  @Test
  void testGetAdjValues() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.friend(friendNames[1]);
    Collection<User> friends = u.getAdjValues();
    assertEquals(1, friends.size(), "Incorrect number of friends");
    assertTrue(friends.contains(v), "Friend not found in collection");
  }

  // Test getFlwValues returns the collection of users being followed
  @Test
  void testGetFlwValues() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.follow(friendNames[1]);
    Collection<User> following = u.getFlwValues();
    assertEquals(1, following.size(), "Incorrect number of followed users");
    assertTrue(following.contains(v), "Followed user not found in collection");
  }

  // Test getAdjAt returns the correct friend for a given name
  @Test
  void testGetAdjAt() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.friend(friendNames[1]);
    assertEquals(v, u.getAdjAt(friendNames[1]), "Incorrect friend returned");
  }

  // Test getFlwAt returns the correct user being followed for a given name
  @Test
  void testGetFlwAt() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.follow(friendNames[1]);
    assertEquals(v, u.getFlwAt(friendNames[1]), "Incorrect user being followed returned");
  }

  // Test removeAdjAt removes the correct friend for a given name
  @Test
  void testRemoveAdjAt() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.friend(friendNames[1]);
    u.removeAdjAt(friendNames[1]);
    assertEquals(0, u.getAdjSize(), "Friend not removed correctly");
  }

  // Test removeFlwAt removes the correct user being followed for a given name
  @Test
  void testRemoveFlwAt() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.follow(friendNames[1]);
    u.removeFlwAt(friendNames[1]);
    assertEquals(0, u.getFlwSize(), "Followed user not removed correctly");
  }

  // Test getAdjSize returns the correct number of friends
  @Test
  void testGetAdjSize() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.friend(friendNames[1]);
    assertEquals(1, u.getAdjSize(), "Incorrect number of friends returned");
  }

  // Test getFlwSize returns the correct number of users being followed
  @Test
  void testGetFlwSize() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.follow(friendNames[1]);
    assertEquals(1, u.getFlwSize(), "Incorrect number of followed users returned");
  }


  // Test friending a user adds them to both users' adj
  @Test
  void testFriend() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    User w = u.friend(friendNames[1]);
    assertEquals(1, u.getAdjSize(), "u's adj has the wrong size");
    assertEquals(1, v.getAdjSize(), "v's adj has the wrong size");
    assertEquals(v, u.getAdjAt(friendNames[1]), "u's adj has the wrong user");
    assertEquals(u, v.getAdjAt(friendNames[0]), "v's adj has the wrong user");
    assertEquals(v, w, "friend returned the wrong user");
  }

  // Test friending a user twice does not add them twice
  @Test
  void testFriendTwice() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    User w = u.friend(friendNames[1]);
    User x = u.friend(friendNames[1]);
    assertEquals(1, u.getAdjSize(), "u's adj has the wrong size");
    assertEquals(1, v.getAdjSize(), "v's adj has the wrong size");
    assertEquals(v, u.getAdjAt(friendNames[1]), "u's adj has the wrong user");
    assertEquals(u, v.getAdjAt(friendNames[0]), "v's adj has the wrong user");
    assertEquals(v, w, "friend returned the wrong user");
    assertEquals(v, x, "friend returned the wrong user");
  }

  // Test friending a user removes any following between the two.
  @Test
  void testFriendWhenFollowed() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.follow(friendNames[1]);
    v.follow(friendNames[0]);
    u.friend(friendNames[1]);
    assertEquals(0, u.getFlwSize(), "u's flw should be empty.");
    assertEquals(0, v.getFlwSize(), "v's flw should be empty.");
  }

  // Test unfriending a user removes them from both users' adj
  @Test
  void testUnfriend() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.friend(friendNames[1]);
    User w = u.unfriend(friendNames[1]);
    assertEquals(0, u.getAdjSize(), "u's adj has the wrong size");
    assertEquals(0, v.getAdjSize(), "v's adj has the wrong size");
    assertEquals(null, u.getAdjAt(friendNames[1]), "u's adj has the wrong user");
    assertEquals(null, v.getAdjAt(friendNames[0]), "v's adj has the wrong user");
    assertEquals(v, w, "unfriend returned the wrong user");
  }

  // Test unfriending a user in the opposite order
  @Test
  void testUnfriendOpposite() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.friend(friendNames[1]);
    User w = v.unfriend(friendNames[0]);
    assertEquals(0, u.getAdjSize(), "u's adj has the wrong size");
    assertEquals(0, v.getAdjSize(), "v's adj has the wrong size");
    assertEquals(null, u.getAdjAt(friendNames[1]), "u's adj has the wrong user");
    assertEquals(null, v.getAdjAt(friendNames[0]), "v's adj has the wrong user");
    assertEquals(u, w, "unfriend returned the wrong user");
  }

  // Test isFriend returns true when the users are friends
  @Test
  void testIsFriendTrue() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.friend(friendNames[1]);
    assertTrue(u.isFriend(v), "isFriend returned false");
  }

  // Test isFriend returns false when the users are not friends
  @Test
  void testIsFriendFalse() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    assertFalse(u.isFriend(v), "isFriend returned true");
  }

  // Test isFollower returns true when the user is following the other.
  @Test
  void testIsFollowerTrue() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.follow(friendNames[1]);
    assertTrue(u.isFollower(v), "isFriend returned false");
  }

  // Test isFollower returns false when the user is not following the other.
  @Test
  void testIsFollowerFalse() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    assertFalse(u.isFollower(v), "isFollower returned true");
  }

  // Test following a user adds them to one users' flw.
  @Test
  void testFollow() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    User w = u.follow(friendNames[1]);
    assertEquals(1, u.getFlwSize(), "u's flw has the wrong size");
    assertEquals(0, v.getFlwSize(), "v's flw has the wrong size");
    assertEquals(v, u.getFlwAt(friendNames[1]), "u's flw has the wrong user");
    assertEquals(null, v.getAdjAt(friendNames[0]), "v's flw shouldn't have any users");
    assertEquals(v, w, "follow returned the wrong user");
  }


  /* Test unfollowing a user, one user unfollowing the other shouldn't impact
   * the other users' flw.
   */
  @Test
  void testUnfollow() {
    User u = new User(friendNames[0]);
    User v = new User(friendNames[1]);
    u.follow(friendNames[1]);
    v.follow(friendNames[0]);
    User w = u.unfollow(friendNames[1]);
    assertEquals(0, u.getFlwSize(), "u's flw has the wrong size");
    assertEquals(1, v.getFlwSize(), "v's flw has the wrong size");
    assertEquals(null, u.getFlwAt(friendNames[1]), "u's flw has the wrong user");
    assertEquals(u, v.getFlwAt(friendNames[0]), "v's flw has the wrong user");
    assertEquals(v, w, "unfollow returned the wrong user");
  }

}
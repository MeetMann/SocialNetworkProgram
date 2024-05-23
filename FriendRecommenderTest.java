import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FriendRecommenderTest {
  private static String [] friendNames = { "Alice", "Bob", "Carol", "Dave", "Eve", "Frank", "Grace"};

  // clear the static users HashMap before each test
  @AfterEach
  void clearUsers() {
    User.users.clear();
  }

  // recommending friends for the same user should make no recommendations
  @Test
  void testSameUser() {
    User u = new User( friendNames[0] );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.makeRecommendations( u, u, al );
    assertEquals( 0, al.size(), "No recommendations should be made for the same user");
  }

  // recommending friends between two users with no friends should make no recommendations
  @Test
  void testNoFriends() {
    User u = new User( friendNames[0] );
    User f = new User( friendNames[1] );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.makeRecommendations( u, f, al );
    assertEquals( 0, al.size(), "No recommendations should be made for two users with no friends");
  }
  // recommending friends between two users with only each other as friends should make no recommendations
  @Test
  void testOneFriend() {
    User u = new User( friendNames[0] );
    User f = new User( friendNames[1] );
    u.friend( f.getName() );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.makeRecommendations( u, f, al );
    assertEquals( 0, al.size(), "No recommendations should be made for two users with only each other as friends");
  }

  // recommending friends between two users where the second has another friend should recommend that friend
  @Test
  void testTwoFriends() {
    User u = new User( friendNames[0] );
    User f = new User( friendNames[1] );
    User g = new User( friendNames[2] );
    u.friend( f.getName() );
    f.friend( g.getName() );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.makeRecommendations( u, f, al );
    assertEquals( 1, al.size(), "Multiple recommendations were made for two users with only each other as friends");
    assertEquals( u.getName() + " and " + g.getName() + " should be friends", al.get( 0 ), "Incorrect recommendation");
  }

  // recommending friends between two users where the second has another friend should recommend that friend in sorted order
  @Test
  void testTwoFriendsSorted() {
    User u = new User( friendNames[2] );
    User f = new User( friendNames[1] );
    User g = new User( friendNames[0] );
    u.friend( f.getName() );
    f.friend( g.getName() );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.makeRecommendations( u, f, al );
    assertEquals( 1, al.size(), "Wrong recommendation count" );
    assertEquals( g.getName() + " and " + u.getName() + " should be friends", al.get( 0 ), "Incorrect recommendation");
  }

  // recommending friends between two users where the first has another friend should return no recommendations
  @Test
  void testTwoFriends2() {
    User u = new User( friendNames[0] );
    User f = new User( friendNames[1] );
    User g = new User( friendNames[2] );
    u.friend( f.getName() );
    f.friend( g.getName() );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.makeRecommendations( f, u, al );
    assertEquals( 0, al.size(), "Wrong recommendation count" );
  }

  /* suggestFollowForNewFriend should recommend a user to follow when
   * u is a friend of f, u follows w, and f does not already follow w.
   */
  @Test
  void testSuggestFollowForNewFriend() {
    User u = new User( friendNames[0] );
    User f = new User( friendNames[1] );
    User w = new User( friendNames[2] );
    u.follow(w.getName());
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.suggestFollowForNewFriend( u, u.friend(f.getName()), al );
    assertEquals( 1, al.size(), "Wrong recommendation count" );
    assertEquals( f.getName() + " should follow " + w.getName(), al.get(0), "Incorrect recommendation");
  }

  /* suggestFollowForNewFriend should not recommend a user to follow when
   * u is a friend of f, u follows w, and f already follows w.
   */
  @Test
  void testShouldNotSuggestFollowForNewFriend() {
    User u = new User( friendNames[0] );
    User f = new User( friendNames[1] );
    User w = new User( friendNames[2] );
    u.follow(w.getName());
    f.follow(w.getName());
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.suggestFollowForNewFriend( u, u.friend(f.getName()), al );
    assertEquals( 0, al.size(), "Wrong recommendation count" );
  }

  /* suggestFollowForNewFriend should not recommend a user to follow when
   * u is null.
   */
  @Test
  void testShouldNotSuggestFollowForNewFriendNull() {
    User u = null;
    User f = new User( friendNames[1] );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.suggestFollowForNewFriend( u, f, al );
    assertEquals( 0, al.size(), "Wrong recommendation count" );
  }

  /* suggestFollow should recommend u's friends to follow when
   * u is a follower of f, and u's friends do not already follow f.
   */
  @Test
  void testSuggestFollow() {
    User u = new User( friendNames[0] );
    User f = new User( friendNames[1] );
    User w = new User( friendNames[2] );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    u.friend(w.getName());
    fr.suggestFollow( u, u.follow(f.getName()), al );
    assertEquals( 1, al.size(), "Wrong recommendation count" );
    assertEquals( w.getName() + " should follow " + f.getName(), al.get(0), "Incorrect recommendation");
  }

  /* suggestFollow should not recommend u's friends to follow when
   * u is a follower of f, and u's friends already follow f.
   */
  @Test
  void testShouldNotSuggestFollowWhenAlreadyFollowing() {
    User u = new User( friendNames[0] );
    User f = new User( friendNames[1] );
    User w = new User( friendNames[2] );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    u.friend(w.getName());
    w.follow(f.getName());
    fr.suggestFollow( u, u.follow(f.getName()), al );
    assertEquals( 0, al.size(), "Wrong recommendation count" );
  }

  // suggestFollow should not recommend a user to follow when u is null.
  @Test
  void testShouldNotSuggestFollowNull() {
    User u = null;
    User f = new User( friendNames[1] );
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.suggestFollow( u, f, al );
    assertEquals( 0, al.size(), "Wrong recommendation count" );
  }

}
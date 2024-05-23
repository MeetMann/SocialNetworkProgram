import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/* FriendRecommender.java
 *
 * This class is used to make friend recommendations for users of a social
 * network. The code is incomplete and contains bugs.
 *
 */
public class FriendRecommender {

  /* recommend
   * Given two users, u and f, and an ArrayList of Strings, al, this method
   * will recommend new friends for u based on the friends of f. The
   * recommendations are added to al. The recommendations are of the form
   * "A and B should be friends" where A and B are the names of the users and
   * A comes before B in sorted order. The method does not return anything so
   * the output is passed back in al.
   */
  public void recommend( User u, User f, ArrayList<String> al ) {
    ArrayList<String> tmp = new ArrayList<String>();
    makeRecommendations( u, f, tmp );
    makeRecommendations( f, u, tmp);
    Collections.sort( tmp );
    String prev = null;
    for( String s : tmp ) {
      if( !s.equals( prev ) ) {
        al.add( s );
        prev = s;
      }
    }
  }

  /* makeRecommendations
   * Given two users, u and f, and an ArrayList of Strings, al, this method
   * will recommend new friends for u based on the friends of f. The
   * recommendations are added to al. The recommendations are of the form
   * "A and B should be friends" where A and B are the names of the users and
   * A comes before B in sorted order. The method does not return anything so
   * the output is passed back in al.
   */
  public void makeRecommendations( User u, User f, ArrayList<String> al ) {
    for( User v : f.getAdjValues() ) {
      if( (u != v) && !u.isFriend( v ) ) {
        if( v.getName().compareTo( u.getName() ) < 0 ) {
          al.add( v.getName() + " and " + u.getName() + " should be friends" );
        } else {
          al.add( u.getName() + " and " + v.getName() + " should be friends" );
        }
      }
    }
  }

  /* suggestFollowForNewFriend
   * Given two newly friended users, u and f, and an ArrayList of Strings, al,
   * this method will recommend new follows for f based on whom u is following.
   * The recommendations are added to al. The recommendations are of the form
   * "A should follow B" where A and B are the names of the users and
   * A comes before B in order. The method does not return anything so
   * the output is passed back in al.
   */
  public void suggestFollowForNewFriend(User u, User f, ArrayList<String> al) {
    if (u != null) {
      for (User v : u.getFlwValues()) {
        if ((f != v) && !f.isFollower(v)) {
          al.add(f.getName() + " should follow " + v.getName());
        }
      }
    }
  }

  /* suggestFollow
   * Given two users, u and f where u has just followed f, and an ArrayList of Strings, al,
   * this method will recommend the friends of u to follow f as long as they aren't already following them.
   * The recommendations are added to al. The recommendations are of the form
   * "A should follow B" where A and B are the names of the users and
   * A comes before B in order. The method does not return anything so the output is passed back in al.
   */
  public void suggestFollow(User u, User f, ArrayList<String> al) {
    if (u != null) {
      for (User v : u.getAdjValues()) {
        if (!v.isFollower(f)) {
          al.add(v.getName() + " should follow " + f.getName());
        }
      }
    }
  }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /* main
     * This method is used to read input from the user and print the output of the
     * friend recommendations. The input is read from the standard input and the
     * output is printed to the standard output.
     */
    public static void main( String [] args ) {
        ArrayList<String> list = new ArrayList<String>();
        Scanner input = new Scanner(System.in);
        int error = 0;

        for(String s = input.nextLine(); !s.equals( "end" ) & error != 1; s = input.nextLine()) {
            Scanner line = new Scanner( s );
            String name = line.next();
            User u = User.find( name );
            String keyword = line.next();
            FriendRecommender fr = new FriendRecommender();

            switch( keyword ) {
                case "joins":
                    assert( u == null );
                    new User( name );
                    if (line.hasNext()) {
                        list.add("Invalid line: " + name + " joins " + line.next());
                        error = 1;
                    }
                    break;
                case "leaves":
                    assert( u != null );
                    u.leave(u.getName());
                    break;
                case "friends":
                    assert( u != null );
                    if (!line.hasNext()) {
                        System.out.println("Invalid line: " + u.getName() + " friends");
                        error = 1;
                    }
                    User f = User.find(line.next());
                    if (f.getName().equals(u.getName())) {
                        list.add("Invalid line: " + u.getName() + " friends " + f.getName());
                        error = 1;
                    }
                    fr.recommend(u, u.friend(f.getName()), list);
                    fr.suggestFollowForNewFriend(u, f, list);
                    break;
                case "unfriends":
                    assert( u != null );
                    u.unfriend( line.next() );
                    break;
                case "follows":
                    assert( u != null );
                    User d = User.find(line.next());
                    if (!u.isFriend(d)) {
                        fr.suggestFollow(u, u.follow(d.getName()), list);
                    }
                    break;
                default:
                    list.add("Invalid line: " + u.getName() + " " + keyword + " " + line.next());
                    error = 1;
            }
        }
        for( String s : list ) {
            System.out.println( s );
        }
    }
}

# SocialNetworkProgram
The Friend Recommender System is a Java application that suggests potential friendships within a social network based on mutual friends. The program processes user actions (joining, friending) and dynamically generates friend recommendations.

Features
User Management: Handles users joining the network.
Friendship Management: Manages bidirectional friendships.
Friend Recommendations: Suggests new friends based on mutual friends.
Design
Built with a robust OOP architecture:

User.java: Represents a user.
UserRelationships.java: Manages friendships.
UserServices.java: Handles user actions and recommendations.
FriendRecommender.java: Main logic for processing actions and generating recommendations.
Usage
Run Main.java to process input and get friend recommendations.

Sample Input:
<ul>
<li>Alice joins</li>
<li>Carol joins</li>
<li>Bob joins</li>
<li>Bob friends Alice</li>
<li>Bob friends Carol</li>
<li>Dave joins</li>
<li>Dave friends Bob</li>
<li>end</li>
</ul>

Includes unit tests to ensure functionality and reliability:
<ul>
<li>UserTest.java</li>
<li>UserServicesTest.java</li>
<li>FriendRecommenderTest.java</li>
</ul>


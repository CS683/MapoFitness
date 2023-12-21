<h1 align="center"><strong>CS683 Project Assignment</strong></h1>
<h2 align="center">Mapo Fitness</h2>
<h3 align="center">Shanghua Yang</h3>


**[Overview](#overview) [2](#overview)**

**[Related Work](#related-work) [2](#related-work)**

**[Requirement Analysis and Testing](#requirement-analysis-and-testing)
[2](#requirement-analysis-and-testing)**

**[Design and Implementation](#design-and-implementation)
[3](#design-and-implementation)**

**[Project Structure](#project-structure) [4](#project-structure)**

**[Timeline](#timeline) [4](#timeline)**

**[Future Work (Optional)](#future-work-optional)
[4](#future-work-optional)**

**[Project Demo Links](#project-demo-links) [4](#project-demo-links)**

# Overview 

The application I want to develop is a fitness and nutrition app. The reason that I want to do this is because I work out a lot and I would like to track my nutrition consumption, calories consumption, physical indicator and work out records etc. However, applications on the market do not fully fulfill my requirements. The target customers of most applications are for normal people instead of athletes or advanced bodybuilders, so for my application, my target customers would be both normal people, bodybuilders, and vegan if I implement the diet feature. 

# Related Work

The applications that I found online are Fitbit and Myfitness Pal. Both applications have the calories tracker feature which will also be in my application. For Fitbit, it focuses more on work out records that includes calorie consumption, BPM and time. For MyFitnessPal, it focuses more on food consumption and nutrition calculation. My application will combine these two applications and includes all features that I mentioned above. In addition to these features, I would like to provide weight records and work out records to my customers based on their goals and physical indicators. However, I will not implement some features in Fitbit and MyFitnessPal such as scanning grocery barcodes to see nutrition or stress and sleep management (probably a Nice-to-have feature if I have time). 

# Requirement Analysis and Testing

<table>
<colgroup>
<col style="width: 19%" />
<col style="width: 80%" />
</colgroup>
<tbody>
<tr class="odd">
<td><em>Title (Essential/Desirable/Optional)</em></td>
<td><em>Sign up, log in and view profile (Essential)</em></td>
</tr>
<tr class="even">
<td><em>Description</em></td>
<td><em>As a user, I want to be able to create an account so that I can see my customized content when I log in next time.</em></td>
</tr>
<tr class="odd">
<td><em>Mockups</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Acceptance tests</em></td>
<td><p><em>When the app is opened, user is required to log in using google account and no sign up is needed.</em></p>
<p><em>After logging in, it will redirect to home page. There is a bottom navigation bar in the bottom of screen: Home and Profile page.</em></p>
</td>
</tr>
<tr class="odd">
<td><em>Test Results</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Status</em></td>
<td><p><em>Iteration 1: Implement UI, setup cloud database for storing user info using firebase.</em></p>
<p><em>Iteration 2: Implement navigation and user entity</em></p>
</td>
</tr>
</tbody>
</table>

<table>
<colgroup>
<col style="width: 30%" />
<col style="width: 70%" />
</colgroup>
<tbody>
<tr class="odd">
<td><em>Title (Essential/Desirable/Optional)</em></td>
<td><em>Weight record (Essential)</em></td>
</tr>
<tr class="even">
<td><em>Description</em></td>
<td><em>As a user, I want to be able to record my daily meals and work out so that the app can calculate calories consumption for me.</em></td>
</tr>
<tr class="odd">
<td><em>Mockups</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Acceptance tests</em></td>
<td>
<p><em>Given a button (Record workout) on the home page.</em></p>
<p><em>When user clicks Record weight, It will appear a bottomsheet that allow user to choose current weight. When user clicks save button, the weight and weight record will be updated and shown in the line chart.</em></p>
</td>
</tr>
<tr class="odd">
<td><em>Test Results</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Status</em></td>
<td>
<p><em>Iteration 1: Implement UI</em></p>
<p><em>Iteration 2: Implement BottomSheet Content</em></p>
<p><em>Iteration 3: Implement Weight Record Line Chart and database Storage</em></p>
</td>
</tr>
</tbody>
</table>

<table>
<colgroup>
<col style="width: 20%" />
<col style="width: 80%" />
</colgroup>
<tbody>
<tr class="odd">
<td><em>Title (Essential/Desirable/Optional)</em></td>
<td><em>Workout record (Essential)</em></td>
</tr>
<tr class="even">
<td><em>Description</em></td>
<td><em>As a user, I want to be able to record my daily meals and work out so that the app can calculate calories consumption for me.</em></td>
</tr>
<tr class="odd">
<td><em>Mockups</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Acceptance tests</em></td>
<td>
<p><em>Given a button (Record workout) on the home page.</em></p>
<p><em>When user clicks Record workout, it will appear a list of workouts, and when the user clicks one of workout in the list, the user then has to choose time of workout, and it will write record in user’s data. Also in the work out page, user can use search bar to search for their desirable activity</em></p>
</td>
</tr>
<tr class="odd">
<td><em>Test Results</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Status</em></td>
<td>
<p><em>Iteration 1: Implement UI</em></p>
<p><em>Iteration 2: Implement work out page, import datasets into firebase database.</em></p>
<p><em>Iteration 3: Implement BottomSheet Content, search bar and database storage</em></p>
</td>
</tr>
</tbody>
</table>

<table>
<colgroup>
<col style="width: 20%" />
<col style="width: 80%" />
</colgroup>
<tbody>
<tr class="odd">
<td><em>Title (Essential/Desirable/Optional)</em></td>
<td><em>Set personal physical indicator (Essential)</em></td>
</tr>
<tr class="even">
<td><em>Description</em></td>
<td><em>As a user, I want to record my physical indicators such as weight, height, BMI, body fat etc so that I can keep track of my health status.</em></td>
</tr>
<tr class="odd">
<td><em>Mockups</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Acceptance tests</em></td>
<td>
<p><em>Given the button, set a physical indicator in the profile page.</em></p>
<p><em>When a user clicks it, it will redirect to a form for the user to fill. When the user clicks the submit button, the data will be written in the user's record.</em></p>
</td>
</tr>
<tr class="odd">
<td><em>Test Results</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Status</em></td>
<td>
<p><em>Iteration 1: </em></p>
<p><em>Iteration 2: Implement UI</em></p>
<p><em>Iteration 3: Implemented personal info page including setting dob, age, height, weight</em></p>
</td>
</tr>
</tbody>
</table>

<table>
<colgroup>
<col style="width: 20%" />
<col style="width: 80%" />
</colgroup>
<tbody>
<tr class="odd">
<td><em>Title (Essential/Desirable/Optional)</em></td>
<td><em>Physical indicator dashboard (Desirable)</em></td>
</tr>
<tr class="even">
<td><em>Description</em></td>
<td><em>As a user, I want to see a dashboard that demonstrates changes in my physical indicators so that I can see my training or diet progress.</em></td>
</tr>
<tr class="odd">
<td><em>Mockups</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Acceptance tests</em></td>
<td>
<p><em>When user change their personal indicator such as height, weight, the body fat, bmi and metabolism will change accordingly. And when user record weight or record work out, the stats will be updated.</em></p>
</td>
</tr>
<tr class="odd">
<td><em>Test Results</em></td>
<td><em></em></td>
</tr>
<tr class="even">
<td><em>Status</em></td>
<td>
<p><em>Iteration 1: </em></p>
<p><em>Iteration 2: Implement UI, implement my stats fetch functions.</em></p>
<p><em>Iteration 3: Implement fetch BMI, bodyfat and metabolism functions</em></p>
</td>
</tr>
</tbody>
</table>


# Design and Implementation



# Project Structure



# Timeline
<table>
<colgroup>
<col style="width: 9%" />
<col style="width: 22%" />
<col style="width: 22%" />
<col style="width: 22%" />
<col style="width: 22%" />
</colgroup>
<tbody>
<tr class="odd">
<td>Iteration</td>
<td><p>Application Requirements</p><p>(Essential/Desirable/Optional)</p></td>
<td>Android Components and Features to be used</td>
</tr>
<tr class="even">
<td>1</td>
<td>Sign-up and login (Essential)<br />Record weight and workout (Essential)</td>
<td>Login, Signup using firebase authentication.<br />UI Elements: Login, signup, profile, and home page, and Weight Record composable (including line chart).<br />Database: Cloud Database for storing user’s personal information using firebase real-time database.</td>
</tr>
<tr class="odd">
<td>2</td>
<td>Physical indicator dashboard (Desirable)<br />Record weight and workout (Essential)</td>
<td>Personal Info screen and bottomsheet layout to set personal information.<br />Database: create weight record in database and implement fetch function for weight record line chart.<br />Implement physical indicator dashboard showing bmi, bodyfat and metabolism based on personal information such as weight and height.</td>
</tr>
<tr class="even">
<td>3</td>
<td>Record weight and workout (Essential)</td>
<td>Database: Import activity calorie burned datasets into firebase real-time database.<br />Implement Work out screen: Creating activity list from work-out database.<br />Implement search bar in work-out screen.<br />Implement bottomsheet in work-out screen.<br />Implement My stats in profile screen.</td>
</tr>
</tbody>
</table>


# Future Work (Optional)

There are several things I can in the future. First, the diet feature has not been implemented yet. The diet feature requires nutrition datasets and enable user to record their diet and app will show nutrition consumptions in the dashboard. Also, it is also possible to implement google fit API to keep track of users’ steps and sleep. Another future work will be implementing customized content for different type of user and generate work-out and diet recommendation. This requires a lot of works including rebuild the structure of user entity and include different contents for different types of users in the database. 

# Project Demo Links


# References  
MPAndroid Chart: https://github.com/PhilJay/MPAndroidChart
Work out Datasets: https://www.kaggle.com/datasets/a5ee8b9d770e65ca566f73016e860e693b7d966a8fa0f24137942a380ce4fc84


# Android-Git-Client

Android application for GitHub. The application uses [Github Developer API](https://developer.github.com/v3/) to fetch all the relevant
user information (The API requires CLIENT_ID and CLIENT_SECRET for OAuth as explained [here](https://developer.github.com/v3/oauth/)).

The following features are available:
* Allows OAuth application login.
* List of all public and private owned/forked Repositories of the user.
* Provides details of each Repository
  * Branches
  * Commits
  * Files/Directories
* Issues assigned, created or closed by the user in the user-owned repositories.
* Gists created by the user.
* Search interface to fetch details about other users.
* Provides Private feeds to keep a track of activities of the `Following` users.
* Following/Unfollowing a user.
* Allowing Users to star/watch/fork a repository.

The following features are yet to be implemented/integrated:
* Allowing Users to edit files in the application.
* Allowing Users to create/close issues.
* Allowing users to create comments on issues.

Will update this list. 


## Screenshots

**This is the landing page if the user is not logged in.**

<img src="https://github.com/codeahead14/Android-Git-Client/blob/Native_Recycler_View/App-Screenshots/Login%20Screen.png" width="200" alt="Login Screen">

**This is the landing page if the user is already logged in.**

<img src="https://github.com/codeahead14/Android-Git-Client/blob/Native_Recycler_View/App-Screenshots/Repositories%20Overview.png" width="200" alt="Repositories">

For more screeenshots visit [here](https://github.com/codeahead14/Android-Git-Client/tree/Native_Recycler_View/App-Screenshots).

## CONTRIBUTIONS

Contributions are welcome and highly appreciated. 

## LICENSE
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

## NOTE
FOR USE ENTER YOUR CLIENT_ID and CLIENT_SECRET [here](https://github.com/codeahead14/Android-Git-Client/edit/master/app/src/main/java/com/example/gaurav/gitfetchapp/MainActivityFragment.java)

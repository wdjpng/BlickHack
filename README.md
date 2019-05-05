# BlickHack

A simple app that converts an image of the [Blick Live Quiz App](https://www.blick.ch/services/apps/livequiz/taeglich-mitspielen-und-geld-gewinnen-willkommen-beim-blick-live-quiz-id8820788.html) into a google search query using text recognition.

## Prerequisites

To test the application you will need a working installation of [Android Studio](https://developer.android.com/studio/install) and [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git).


## Installation

Firstly we will download the repository from GitHub

```
git clone https://github.com/wdjpng/BlickHack/
```
Then we will open the project in Android Studio
```
Click on Files ... Open and then select /path_to_your_files/BlickHack
```

After the Application is built (BlickHack: syncing... is replaced by Project setup: synced successfully)

## Running
After installing we can now [run the app](https://developer.android.com/studio/run). Now select an image or take a new one (by clicking on the respective icons) of a question from the [Blick Live Quiz App](https://www.blick.ch/services/apps/livequiz/taeglich-mitspielen-und-geld-gewinnen-willkommen-beim-blick-live-quiz-id8820788.html).

The application should now look something like this

<p align="center">
  <img width="336" height="685" src="https://i.ibb.co/58dqZYK/Screenshot-2019-05-04-21-43-06.png">
</p>

## Built With

* [Android Studio and Java](https://developer.android.com/studio) - The base for developing an Android application
* [Firebase Text Recognition](https://firebase.google.com/docs/ml-kit/recognize-text) - For on device optical character recogntion

## Contributing
If you have any remarks, bugs or interesting new ideas you can just send <a href="mailto:muenzel.lukas@gmail.com?">me</a> an email or create a pull request, this project is open for changes. 

## Authors

* **Lukas Münzel** - *Currently the only one contributing to this project* - [wdjpng](https://github.com/wdjpng)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Annotations

* Please note that there is a authentification file for the Google on device ocr in this project to be able to run it, if you would like to use other firebase features please register [your own app on firebase](https://firebase.google.com/docs/android/setup)




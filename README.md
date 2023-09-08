# Video Summary
Hey everyone, welcome to the project page.

This project is a simple command line tool that helps you transcript any audios and summarize it! 

Currently, it only supports audio input, but additional direct video support will be added at a later date.


## Prerequisites
To use this program, you will need the following:
* Azure AI Speech subscriptions.
* Open AI API key and credits.
* ffmpeg installed on your machine (to convert videos into the required audio PCM format).
* Recommended OS: MacOS version 10.14 or later. Linux and Windows could also work, but it may require additional setup. [Read more](https://learn.microsoft.com/en-us/azure/ai-services/speech-service/quickstarts/setup-platform?tabs=windows%2Cubuntu%2Cdotnetcli%2Cdotnet%2Cjre%2Cmaven%2Cnodejs%2Cmac%2Cpypi&pivots=programming-language-java).

### How to convert a video into an audio
There are many ways to do that. Here, we will use `ffmpeg` for example.
1. Install `ffmpeg`. We will use `homebrew` to install `ffmpeg`. You can also download the binary directly.
2. Run `brew install ffmpeg`
3. Convert the video file into an audio file
```
// convert the input video into a mono 16000 hz pcm wav format audio
ffmpeg -i <video> -ac 1 -ar 16000 -acodec pcm_s16le -f wav <output name>
```

## How to run this program
1. run `gradle build` or `./gradlew build`
2. switch to the build/libs folder and run the program using the following command
`java -jar <the jar file> <speech key> <speech region> <openAI API key> `
3. Follow the prompt: enter the file path (relative) or 0 to exit
4. you will be able to see the transcript output as well as the summary!

## Limitation
* As a demo app, currently it only supports up tp 10-minute (hard-coded in the code) video.
* Like mentioned in the beginning, it currently supports audio (mono, 16khz, pcm) only.
* As a prototype, error handling is mostly skipped assuming the app only functions in the happy path.
* More fine-tuning of the based models will be worked on.

## Demo
_For privacy reasons, java command line with secrets is purposely removed from the demo screen recording._

![demo](https://github.com/davidle97/video-summary/assets/144196681/9eb3c683-9383-4d49-b8d8-f9130fc3e4ba)


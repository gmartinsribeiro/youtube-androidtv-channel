# Youtube AndroidTV Channel
Using a youtube playlist as an AndroidTV Channel (the channel code base is from [this project](https://github.com/googlesamples/androidtv-sample-inputs))

# Usage
You simply need to replace two values in res/strings.xml file with your own to have your working channel: your youtube API key in order to list all the videos from a playlist, and your playlist ID in order to fetch those videos and create a channel.

```xml
<resources>
  ...
   <string name="youtube_apikey" translatable="false">YOUR APIKEY</string>
   <string name="youtube_playlistid" translatable="false">YOUR PLAYLISTID</string>
</resources>
```
Youtube API Key refers to your server API Key that you can request from Google.

Playlist ID refers to the unique ID from your playlist.

E.g. For this playlist https://www.youtube.com/playlist?list=PLL9VNKZEYoMnKx-Tll69ZgYJQ4YnhsX1C, the id is PLL9VNKZEYoMnKx-Tll69ZgYJQ4YnhsX1C

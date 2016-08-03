# Youtube AndroidTV Channel
Using a youtube playlist as an AndroidTV Channel (the channel code base is from [this project](https://github.com/googlesamples/androidtv-sample-inputs))

## Usage
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

Finally you can just build the project, install the APK on your TV and have a working streaming channel!

## Further improvements

### Service
For now the project uses KEEPVID service to provide direct links for the youtube videos. This is somewhat unreliable as the website can change in the future. Further improvements can be made in this front in order to find a more reliable service/way to provide direct links.

### Channels
For now the project only supports 1 channel for 1 playlist, however adjustments can be made so you can have more channels for more playlists. If you're looking to explore this now, take a look at the util package and the classes in them.

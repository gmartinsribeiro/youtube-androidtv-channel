package com.aptoide.pt.aptoidechannel.rich;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.aptoide.pt.aptoidechannel.R;
import com.aptoide.pt.aptoidechannel.xmltv.XmlTvParser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Static helper methods for fetching the channel feed.
 */
public class RichFeedUtil {
    private static final String TAG = "RichFeedUtil";

    // A key for the channel display number used in the app link intent from the xmltv_feed.
    public static final String EXTRA_DISPLAY_NUMBER = "display-number";

    private static XmlTvParser.TvListing sSampleTvListing;

    // For this sample we will use the local XML TV feed. In your real app, you will want to use a
    // remote feed to provide your users with up to date channel listings.
    private static final boolean USE_LOCAL_XML_FEED = true;

    private static final int URLCONNECTION_CONNECTION_TIMEOUT_MS = 3000;  // 3 sec
    private static final int URLCONNECTION_READ_TIMEOUT_MS = 10000;  // 10 sec

    private RichFeedUtil() {
    }

    public static XmlTvParser.TvListing getRichTvListings(Context context) {

        File privateFile = new File(context.getFilesDir(), "aptoidexmldescriptor.xml");
            Uri internal = Uri.fromFile(privateFile);

        Uri catalogUri = USE_LOCAL_XML_FEED
                ? internal
                : Uri.parse(context.getResources().getString(R.string.rich_input_feed_url))
                .normalizeScheme();
        if (sSampleTvListing != null) {
            return sSampleTvListing;
        }

        try (InputStream inputStream = getInputStream(context, catalogUri)) {
            sSampleTvListing = XmlTvParser.parse(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "Error in fetching " + catalogUri, e);
        }
        return sSampleTvListing;
    }

    public static InputStream getInputStream(Context context, Uri uri) throws IOException {
        InputStream inputStream;
        if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.getScheme())
                || ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.getScheme())
                || ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            inputStream = context.getContentResolver().openInputStream(uri);
        } else {
            URLConnection urlConnection = new URL(uri.toString()).openConnection();
            urlConnection.setConnectTimeout(URLCONNECTION_CONNECTION_TIMEOUT_MS);
            urlConnection.setReadTimeout(URLCONNECTION_READ_TIMEOUT_MS);
            inputStream = urlConnection.getInputStream();
        }

        return inputStream == null ? null : new BufferedInputStream(inputStream);
    }
}

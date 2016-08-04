package com.aptoide.pt.aptoidechannel.player;

import android.annotation.TargetApi;
import android.media.MediaDrm.KeyRequest;
import android.media.MediaDrm.ProvisionRequest;
import android.text.TextUtils;

import com.google.android.exoplayer.drm.MediaDrmCallback;
import com.google.android.exoplayer.util.Util;

import java.io.IOException;
import java.util.UUID;

/**
 * A {@link MediaDrmCallback} for Widevine test content.
 * <p>
 * This code was originally taken from the ExoPlayer demo application.
 */
@TargetApi(18)
public class WidevineTestMediaDrmCallback implements MediaDrmCallback {

    private static final String WIDEVINE_GTS_DEFAULT_BASE_URI =
            "https://proxy.uat.widevine.com/proxy";

    private final String defaultUri;

    public WidevineTestMediaDrmCallback(String contentId, String provider) {
        String params = "?video_id=" + contentId + "&provider=" + provider;
        defaultUri = WIDEVINE_GTS_DEFAULT_BASE_URI + params;
    }

    @Override
    public byte[] executeProvisionRequest(UUID uuid, ProvisionRequest request) throws IOException {
        String url = request.getDefaultUrl() + "&signedRequest=" + new String(request.getData());
        return Util.executePost(url, null, null);
    }

    @Override
    public byte[] executeKeyRequest(UUID uuid, KeyRequest request) throws IOException {
        String url = request.getDefaultUrl();
        if (TextUtils.isEmpty(url)) {
            url = defaultUri;
        }
        return Util.executePost(url, request.getData(), null);
    }

}

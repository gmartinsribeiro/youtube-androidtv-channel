package com.aptoide.pt.aptoidechannel.rich;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.aptoide.pt.aptoidechannel.syncservice.SyncJobService;
import com.aptoide.pt.aptoidechannel.syncservice.SyncUtils;

import java.util.List;

/**
 * This BroadcastReceiver is set up to make sure sync job can schedule after reboot. Because
 * JobScheduler doesn't work well on reboot scheduler on L/L-MR1.
 */
public class RichBootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // If there are not pending jobs. Create a sync job and schedule it.
        List<JobInfo> pendingJobs = jobScheduler.getAllPendingJobs();
        if (pendingJobs.isEmpty()) {
            String inputId = context.getSharedPreferences(SyncJobService.PREFERENCE_EPG_SYNC,
                    Context.MODE_PRIVATE).getString(SyncJobService.BUNDLE_KEY_INPUT_ID, null);
            if (inputId != null) {
                // Set up periodic sync only when input has set up.
                SyncUtils.setUpPeriodicSync(context, inputId);
            }
            return;
        }
        // On L/L-MR1, reschedule the pending jobs.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            for (JobInfo job : pendingJobs) {
                if (job.isPersisted()) {
                    jobScheduler.schedule(job);
                }
            }
        }
    }
}

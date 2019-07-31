package a207project.fall18.GameCenter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@SuppressLint("AppCompatCustomView")
public class TimerTextView extends TextView {

    private static final int DEFAULT_INTERVAL = 1000;
    private Timer timer = new Timer();
    private long startTime;

    public TimerTextView(Context context) {
        super(context);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopTimer();
    }

    @Override protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (VISIBLE == visibility) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    /**
     * @return the starting time of the game, with respect to System current time in milliseconds
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the starting time of the game, with respect to System current time in milliseconds
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Start the timer
     */
    public void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                if (null == getHandler()) {
                    return;
                }
                getHandler().post(() -> setText(getDurationBreakdown(
                        System.currentTimeMillis() - startTime)));
            }
        }, 0, (long) DEFAULT_INTERVAL);
    }

    /**
     * Stop the timer
     */
    public void stopTimer() {
        timer.cancel();
    }

    /**
     * @param diff difference between start time and stop time
     * @return the duration in milliseconds
     */
    public String getDurationBreakdown(long diff) {
        long millis = diff;
        if (millis < 0) {
            return "00:00:00";
        }
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        return String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds);
    }
}
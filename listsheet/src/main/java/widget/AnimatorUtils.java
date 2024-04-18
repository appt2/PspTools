package com.ninjacoder.listshset.library.widget;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.animation.ObjectAnimator;
import java.util.Timer;
import java.util.TimerTask;

public class AnimatorUtils {
  private static TimerTask timer;
  private static Timer timerlog = new Timer();

  static void Animator(View view, String propertyName, double value, double duration) {
    ObjectAnimator anim = new ObjectAnimator();
    anim.setTarget(view);
    anim.setPropertyName(propertyName);
    anim.setFloatValues((float) value);
    anim.setDuration((long) duration);
    anim.start();
  }

  public static void ClickAnimation(final View view) {
    view.setOnTouchListener(
        (v, event) -> {
          switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              {
                Animator(view, "elevation", 5, 70);
                Animator(view, "scaleX", 0.9d, 70);
                Animator(view, "scaleY", 0.9, 70);
                timer =
                    new TimerTask() {
                      @Override
                      public void run() {
                        runOnUiThread(
                            () -> {
                              Animator(view, "elevation", 1, 100);
                              Animator(view, "scaleX", 0.88d, 100);
                              Animator(view, "scaleY", 0.88d, 100);
                            });
                      }
                    };
                timerlog.schedule(timer, 70);
                break;
              }
            case MotionEvent.ACTION_UP:
              {
                timer.cancel();
                Animator(view, "elevation", 40, 100);
                Animator(view, "scaleX", 1.1d, 100);
                Animator(view, "scaleY", 1.1d, 100);
                timer =
                    new TimerTask() {
                      @Override
                      public void run() {
                        runOnUiThread(
                            () -> {
                              Animator(view, "elevation", 25, 100);
                              Animator(view, "scaleX", 1.0d, 100);
                              Animator(view, "scaleY", 1.0d, 100);
                            });
                      }
                    };
                timerlog.schedule(timer, 100);
                break;
              }
          }
          return false;
        });
  }

  static void runOnUiThread(Runnable r) {
    var handler = new Handler(Looper.getMainLooper());
    handler.post(r);
  }
}

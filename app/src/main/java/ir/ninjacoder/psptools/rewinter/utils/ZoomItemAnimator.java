package ir.ninjacoder.psptools.rewinter.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Rect;

import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ZoomItemAnimator extends RecyclerView.ItemAnimator
    implements ScaleGestureDetector.OnScaleGestureListener {
  private static final String TAG = "ZoomItemAnimator";

  private GridLayoutManager layoutManager;
  private ZoomingRecyclerView recyclerView;
  private boolean isRunning;
  private ArrayList<AnimatedItem> animatedSet = new ArrayList<>();
  private AnimatorSet animator;
  private Scale scale;

  private ScaleManager scaleHandler =
      new ScaleManager(
          new ScaleManager.OnScaleChangedListener() {
            @Override
            public void onScaleChanged(Scale scale) {
              if (ZoomItemAnimator.this.scale != null) {
                animateItems();
              } else {
                setScale(scale);
              }
            }
          });

  private void setScale(Scale scale) {
    this.scale = scale;
    switch (scale.getType()) {
      case Scale.TYPE_SCALE_DOWN:
        {
          decrementSpanCount();
          break;
        }
      case Scale.TYPE_SCALE_UP:
        {
          incrementSpanCount();
          break;
        }
    }
  }

  public void setup(ZoomingRecyclerView recyclerView) {
    this.recyclerView = recyclerView;
    this.layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
    this.recyclerView.setOnScaleGestureListener(this);
    this.recyclerView.setItemAnimator(this);
  }

  @Override
  public boolean animateDisappearance(
      @NonNull RecyclerView.ViewHolder viewHolder,
      @NonNull ItemHolderInfo preLayoutInfo,
      @Nullable ItemHolderInfo postLayoutInfo) {
    AnimatedItem ai =
        new AnimatedItem.Builder()
            .setViewHolder(viewHolder)
            .setPreRect(preLayoutInfo)
            .setPostRect(postLayoutInfo)
            .setType(AnimatedItem.Type.DISAPPEARANCE)
            .build();
    animatedSet.add(ai);
    return false;
  }

  @Override
  public boolean animateAppearance(
      @NonNull RecyclerView.ViewHolder viewHolder,
      @Nullable ItemHolderInfo preLayoutInfo,
      @NonNull ItemHolderInfo postLayoutInfo) {
    AnimatedItem ai =
        new AnimatedItem.Builder()
            .setViewHolder(viewHolder)
            .setPreRect(preLayoutInfo)
            .setPostRect(postLayoutInfo)
            .setType(AnimatedItem.Type.APPEARANCE)
            .build();
    animatedSet.add(ai);
    return false;
  }

  @Override
  public boolean animatePersistence(
      @NonNull RecyclerView.ViewHolder viewHolder,
      @NonNull ItemHolderInfo preLayoutInfo,
      @NonNull ItemHolderInfo postLayoutInfo) {
    AnimatedItem ai =
        new AnimatedItem.Builder()
            .setViewHolder(viewHolder)
            .setPreRect(preLayoutInfo)
            .setPostRect(postLayoutInfo)
            .setType(AnimatedItem.Type.PERSISTENCE)
            .build();
    animatedSet.add(ai);
    return false;
  }

  @Override
  public boolean animateChange(
      @NonNull RecyclerView.ViewHolder oldHolder,
      @NonNull RecyclerView.ViewHolder newHolder,
      @NonNull ItemHolderInfo preLayoutInfo,
      @NonNull ItemHolderInfo postLayoutInfo) {
    AnimatedItem ai =
        new AnimatedItem.Builder()
            .setViewHolder(newHolder)
            .setPreRect(preLayoutInfo)
            .setPostRect(postLayoutInfo)
            .setType(AnimatedItem.Type.CHANGE)
            .build();
    animatedSet.add(ai);
    return false;
  }

  @Override
  public void runPendingAnimations() {}

  @Override
  public void endAnimation(RecyclerView.ViewHolder item) {}

  @Override
  public void endAnimations() {}

  @Override
  public boolean isRunning() {
    return isRunning || (animator != null && animator.isRunning());
  }

  @Override
  public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
    return true;
  }

  @Override
  public boolean onScale(ScaleGestureDetector detector) {
    onScale(detector.getScaleFactor());
    return true;
  }

  @Override
  public boolean onScaleBegin(ScaleGestureDetector detector) {
    animatedSet.clear();
    scaleHandler.reset();
    onScale(detector.getScaleFactor());
    return true;
  }

  @Override
  public void onScaleEnd(ScaleGestureDetector detector) {
    isRunning = false;
    finishAnimation(new ArrayList<>(animatedSet));
    scale = null;
    animatedSet.clear();
    scaleHandler.reset();
  }

  private void finishAnimation(final ArrayList<AnimatedItem> items) {
    animator = new AnimatorSet();
    long duration = (long) (500 * (1.0f - scale.getScale()));
    animator.setDuration(duration);
    animator.setInterpolator(new DecelerateInterpolator());
    animator.addListener(
        new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            isRunning = false;
            ZoomItemAnimator.this.animator = null;
          }
        });

    List<Animator> animators = new ArrayList<>();
    for (AnimatedItem ai : items) {
      addItemAnimators(ai, animators);
    }

    animator.playTogether(animators);
    animator.start();
  }

  private void addItemAnimators(final AnimatedItem ai, List<Animator> animators) {
    Rect post = ai.getPostRect();

    final View view = ai.getViewHolder().itemView;

    Animator viewAnimator =
        ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofInt("top", post.top),
            PropertyValuesHolder.ofInt("left", post.left),
            PropertyValuesHolder.ofInt("bottom", post.bottom),
            PropertyValuesHolder.ofInt("right", post.right));
    viewAnimator.addListener(
        new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            dispatchAnimationFinished(ai.getViewHolder());
          }
        });
    animators.add(viewAnimator);
  }

  private void onScale(float incrementalScaleFactor) {
    scaleHandler.onScale(incrementalScaleFactor);
  }

  private void decrementSpanCount() {
    int spanCount = layoutManager.getSpanCount();
    if (spanCount > 1) {
      layoutManager.setSpanCount(--spanCount);
      notifyDataSetChanged();
      isRunning = true;
    } else {
      scaleHandler.reset();
    }
  }

  private void incrementSpanCount() {
    int spanCount = layoutManager.getSpanCount();
    layoutManager.setSpanCount(++spanCount);
    notifyDataSetChanged();
    isRunning = true;
  }

  private void notifyDataSetChanged() {
    recyclerView.getAdapter().notifyDataSetChanged();
  }

  private void animateItems() {
    for (AnimatedItem h : animatedSet) {
      scaleItem(h);
    }
  }

  private void scaleItem(AnimatedItem h) {
    View itemVIew = h.getViewHolder().itemView;
    int top =
        h.getPreRect().top + (int) (scale.getScale() * (h.getPostRect().top - h.getPreRect().top));
    int left =
        h.getPreRect().left
            + (int) (scale.getScale() * (h.getPostRect().left - h.getPreRect().left));
    int bottom =
        h.getPreRect().bottom
            + (int) (scale.getScale() * (h.getPostRect().bottom - h.getPreRect().bottom));
    int right =
        h.getPreRect().right
            + (int) (scale.getScale() * (h.getPostRect().right - h.getPreRect().right));
    itemVIew.setTop(top);
    itemVIew.setLeft(left);
    itemVIew.setBottom(bottom);
    itemVIew.setRight(right);
  }


}

package com.ninjacoder.listshset.library.widget;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import android.view.animation.ScaleAnimation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;

public class CustomItemAnimator extends RecyclerView.ItemAnimator {

  private Context context;

  public CustomItemAnimator(Context context) {
    this.context = context;
  }

  @Override
  public boolean animateDisappearance(
      ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
    AnimationSet animationSet = new AnimationSet(true);
    Animation alpha = new AlphaAnimation(1, 0);
    alpha.setDuration(500);
    animationSet.addAnimation(alpha);
    viewHolder.itemView.startAnimation(animationSet);
    return true;
  }

  @Override
  public boolean animateAppearance(
      ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
    AnimationSet animationSet = new AnimationSet(true);
    Animation alpha = new AlphaAnimation(0, 1);
    alpha.setDuration(500);
    animationSet.addAnimation(alpha);
    viewHolder.itemView.startAnimation(animationSet);
    return true;
  }

  @Override
  public boolean animatePersistence(
      ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
    // Add persistent animation here
    return false;
  }

  @Override
  public boolean animateChange(
      ViewHolder oldHolder,
      ViewHolder newHolder,
      ItemHolderInfo preLayoutInfo,
      ItemHolderInfo postLayoutInfo) {
    AnimationSet animationSet = new AnimationSet(true);
    Animation alpha = new AlphaAnimation(1, 0);
    alpha.setDuration(500);
    animationSet.addAnimation(alpha);
    newHolder.itemView.startAnimation(animationSet);
    return true;
  }

  @Override
  public void runPendingAnimations() {
    
  }

  @Override
  public void endAnimation(ViewHolder item) {
    item.itemView.clearAnimation();
  }

  @Override
  public void endAnimations() {
  }

  @Override
  public boolean isRunning() {
    return false;
  }

  @Override
  public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
    // Add animations for updated ViewHolder here
    AnimationSet animationSet = new AnimationSet(true);
    Animation scaleUp = new ScaleAnimation(0.5f, 1f, 0.5f, 1f);
    scaleUp.setDuration(500);
    animationSet.addAnimation(scaleUp);
    viewHolder.itemView.startAnimation(animationSet);
    return true;
  }
}

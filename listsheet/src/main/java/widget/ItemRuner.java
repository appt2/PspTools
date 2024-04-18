package com.ninjacoder.listshset.library.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.divider.MaterialDivider;
import com.ninjacoder.listshset.library.R;
import com.ninjacoder.listshset.library.adapter.ListAdapter;
import com.ninjacoder.listshset.library.interfaces.OnItemClickEvent;
import com.ninjacoder.listshset.library.model.SheetModel;
import java.util.ArrayList;
import java.util.List;

public class ItemRuner {
  protected Context context;

  protected RecyclerView listview;
  protected List<SheetModel> list = new ArrayList<>();
  protected BottomSheetDialog dialog;
  protected SheetModel model;
  protected ListAdapter ad;
  protected View view;
  protected TextView title;
  private boolean isLayout2 = false;
  protected static String TAG = ItemRuner.class.getSimpleName();
  protected MaterialDivider divar;

  public ItemRuner(Context context) {
    this.context = context;
    view = LayoutInflater.from(context).inflate(R.layout.layout_sheet_main, null);
    title = view.findViewById(R.id.title);
    listview = view.findViewById(R.id.listdata);
    divar = view.findViewById(R.id.diver);
    listview.setItemAnimator(new CustomItemAnimator(context));
    dialog = new BottomSheetDialog(context);
    dialog.setContentView(view);
  }

  public void setCancelable(boolean boll) {
    dialog.setCancelable(boll);
  }

  public void setDismiss() {
    dialog.dismiss();
  }

  public void setCallBack(OnItemClickEvent ev) {
    ad = new ListAdapter(list, ev);
    listview.setAdapter(ad);
    listview.setLayoutManager(new LinearLayoutManager(context));
  }

  public void addItem(String name, String sub, int icon, boolean bool) {
    list.add(new SheetModel(name, sub, icon, bool));
    setSubShow(true);
  }

  public void addItem(String name, int icon, boolean bool) {
    list.add(new SheetModel(name, "", icon, bool));
    setSubShow(false);
  }

  public void addItem(String name, int icon) {
    list.add(new SheetModel(name, "", icon, true));
    setSubShow(false);
  }

  public void addItem(String name) {
    list.add(new SheetModel(name, "", 0, true));
    setSubShow(false);
  }

  public void removed(int pos) {
    if (pos >= 0 && pos < list.size()) {
      list.remove(pos);
      Log.e("Item : ", String.valueOf(pos));
    } else {
      Log.e(TAG, "Invalid position: " + pos);
      throw new IndexOutOfBoundsException("Invalid position: " + pos);
    }
  }

  public void show() {
    dialog.show();
  }

  public void setCustomView(@IdRes int res) {
    dialog.setContentView(res);
  }

  public void setCustomView(View view) {
    dialog.setContentView(view);
  }

  public void setTextColors(int colors) {
    ad.setTextColor(colors);
  }

  public void setColorFilter(int colors) {
    ad.setColorFilter(colors);
  }

  public void setTitle(String str) {
    title.setText(str);
  }

  public void setTitleColor(int color) {
    title.setTextColor(color);
  }

  public void serDivarColor(int color) {
    divar.setDividerColor(color);
  }

  public void serDivarColorFromRes(@IdRes int color) {
    divar.setDividerColorResource(color);
  }

  public void setSheetBackground(int color) {
    dialog.getWindow().getDecorView().setBackgroundColor(color);
  }

  public void setAnimator(boolean bo) {
    ad.setAnimatorItem(bo);
  }

  public void DataRomved(int pos) {
    listview.setAdapter(ad);
    listview.getAdapter().notifyItemRemoved(pos);
  }

  public void DataRefresh(int pos) {
    listview.setAdapter(ad);
    listview.getAdapter().notifyItemChanged(pos);
  }

  public void setLayoutChange(boolean is) {
    if (ad != null) {
      ad.setLayoutChange(is);
      listview.setAdapter(ad);
      listview.getAdapter().notifyDataSetChanged();
    }
  }

  public void setSubShow(boolean is) {
    if (ad != null) ad.showSub(is);
  }

  public void setShowIcon(boolean show) {
    if (ad != null) {
      ad.showIcon(show);
    }
  }

  public void setIconFromRes(int ic, boolean isuser) {
    if (ad != null) {
      ad.setIconFromRes(ic, isuser);
    }
  }
}

package ir.ninjacoder.psptools.rewinter;

import android.graphics.Color;
import android.net.Uri;
import android.content.Intent;
import android.os.Build;
import android.view.ViewPropertyAnimator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ninjacoder.listshset.library.interfaces.OnItemClickEvent;
import com.ninjacoder.listshset.library.widget.ItemRuner;
import ir.ninjacoder.psptools.rewinter.adapters.ToolBarItemFileList;
import ir.ninjacoder.psptools.rewinter.dialogs.ExitUtils;
import ir.ninjacoder.psptools.rewinter.interfaces.OnTreeViewClick;
import ir.ninjacoder.psptools.rewinter.utils.FileSortByName;
import ir.ninjacoder.psptools.rewinter.utils.MatetialColorUtils;
import ir.ninjacoder.psptools.rewinter.utils.ZoomItemAnimator;
import java.util.ArrayList;
import java.util.Collections;
import org.ppsspp.ppsspp.PpssppActivity;
import android.util.Log;
import android.view.View;
import androidx.activity.OnBackPressedCallback;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.material.color.MaterialColors;
import ir.ninjacoder.psptools.rewinter.adapters.FileListAdapter;
import ir.ninjacoder.psptools.rewinter.databinding.ActivityMainBinding;
import ir.ninjacoder.psptools.rewinter.dialogs.DialogMakeFile;
import ir.ninjacoder.psptools.rewinter.interfaces.OnItemClick;
import ir.ninjacoder.psptools.rewinter.utils.BaseCompat;
import ir.ninjacoder.psptools.rewinter.utils.FileUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import static android.view.View.VISIBLE;
import static android.view.View.GONE;

public class MainActivity extends BaseCompat implements OnItemClick {
  private ActivityMainBinding binding;
  private FileListAdapter filelist;
  private GridLayoutManager manger;
  private File file;
  protected List<File> fileList;
  protected String path = "";
  protected ItemTouchHelper.SimpleCallback simpleItemTouchCallback;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);
    setTitle("PT");

    binding.toolbar.setTitleTextColor(
        MaterialColors.getColor(
            binding.toolbar, com.google.android.material.R.attr.colorPrimary, 0));
    path = FileUtil.getExternalStorageDir();
    reloadFile(path);
    onBack();
    listCh();
    tests();

    binding.fb.setOnClickListener(
        x -> {
          ItemRuner runer = new ItemRuner(this);
          runer.addItem("Create a folder", R.drawable.ic_material_folders);
          runer.addItem("Settings");

          runer.setCallBack(
              new OnItemClickEvent() {

                @Override
                public void onClickItem(int pos) {
                  switch (pos) {
                    case 0:
                      {
                        var dialog =
                            new DialogMakeFile(
                                MainActivity.this,
                                file.getAbsolutePath(),
                                () -> {
                                  reloadFile(file.getAbsolutePath());
                                });
                        runer.setDismiss();
                        break;
                      }
                    case 1:
                      {
                        Intent i = new Intent(getApplicationContext(), SettingsAcivity.class);
                        startActivity(i);
                        runer.setDismiss();
                        break;
                      }
                  }
                }

                @Override
                public void onLongItem(int pos) {}
              });
          runer.setTextColors(
              MaterialColors.getColor(MainActivity.this, MatetialColorUtils.getColorPrimary, 0));
          runer.setColorFilter(
              MaterialColors.getColor(MainActivity.this, MatetialColorUtils.getColorPrimary, 0));
          runer.serDivarColor(
              MaterialColors.getColor(MainActivity.this, MatetialColorUtils.getColorPrimary, 0));
          runer.setTitleColor(
              MaterialColors.getColor(MainActivity.this, MatetialColorUtils.getColorPrimary, 0));
          runer.setTitle("Psp tools");
          runer.setAnimator(true);
          //  runer.setSheetBackground(Color.BLACK);
          runer.setLayoutChange(false);
          runer.setSubShow(false);
          runer.setShowIcon(true);
          runer.show();
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    this.binding = null;
  }

  void onBack() {
    getOnBackPressedDispatcher()
        .addCallback(
            this,
            new OnBackPressedCallback(true) {
              @Override
              public void handleOnBackPressed() {
                if (file != null && !file.getAbsolutePath().equals(path)) {
                  reloadFile(file.getParent());
                } else {
                  ExitUtils u = new ExitUtils(MainActivity.this);
                }
              }
            });
  }

  void reloadFile(String path) {

    showPrograss(true);
    new Thread(
            () -> {
              file = new File(path);
              File[] files = file.listFiles();
              fileList = new ArrayList<>(Arrays.asList(files));
              Collections.sort(fileList, FileSortByName.sortNameAsc());
              filelist = new FileListAdapter(fileList, this);
              manger = new GridLayoutManager(this, 2);
              runOnUiThread(
                  () -> {
                    binding.rv.setAdapter(filelist);
                    binding.rv.setLayoutManager(manger);
                    var itemAnimator = new ZoomItemAnimator();
                    itemAnimator.setup(binding.rv);
                    showPrograss(false);
                  });
            })
        .start();
    List<String> ma = spiltIntoBreadcrumbItems(path);
    binding.barLayout.setLayoutManager(
        new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    binding.barLayout.setAdapter(
        new ToolBarItemFileList(
            ma,
            new OnTreeViewClick() {

              @Override
              public void onTree(File file, int pos) {
                if (file != null && !file.getAbsolutePath().equals(path)) {
                  reloadFile(file.getParent());
                } else {
                  finishAffinity();
                }
              }
            }));
    binding.barLayout.smoothScrollToPosition(ma.size());
  }

  @Override
  public void onClick(File file, int pos, View view) {
    // TODO: Implement this method
    if (file != null) {
      if (file.isDirectory()) {
        reloadFile(file.getAbsolutePath());
      } else if (file.getName().endsWith(".iso") || file.getName().endsWith(".cso")) {
        Uri uri = Uri.fromFile(file);
        respondToShortcutRequest(uri);
      }
    }
  }

  void listCh() {
    if (fileList == null || !fileList.isEmpty()) {
      binding.emptyview.setVisibility(View.GONE);
      binding.rv.setVisibility(View.VISIBLE);
    } else {
      binding.emptyview.setVisibility(View.VISIBLE);
      binding.rv.setVisibility(View.GONE);
    }
  }

  private void respondToShortcutRequest(Uri uri) {

    Intent shortcutIntent = new Intent(getApplicationContext(), PpssppActivity.class);
    shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    Log.i("TAG", "Shortcut URI: " + uri.toString());
    shortcutIntent.setData(uri);
    String path = uri.toString();
    shortcutIntent.putExtra(PpssppActivity.SHORTCUT_EXTRA_KEY, path);
    startActivity(shortcutIntent);
  }

  void showPrograss(boolean show) {
    binding.prograssBar.setVisibility(show ? VISIBLE : GONE);
  }

  public List<String> spiltIntoBreadcrumbItems(String filePath) {
    String separator = "/";
    String[] items = filePath.split(separator);
    List<String> filteredItems = new ArrayList<>();
    for (String item : items) {
      if (!item.trim().isEmpty()) {
        filteredItems.add(item);
      }
    }
    if (filteredItems.size() >= 3
        && filteredItems.get(0).equals("storage")
        && filteredItems.get(1).equals("emulated")
        && filteredItems.get(2).equals("0")) {
      List<String> combinedItems = new ArrayList<>();
      combinedItems.add(Build.MANUFACTURER + " " + Build.MODEL);
      combinedItems.addAll(filteredItems.subList(3, filteredItems.size()));
      return combinedItems;
    }
    return filteredItems;
  }

  void tests() {
    simpleItemTouchCallback =
        new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
          @Override
          public boolean onMove(
              RecyclerView recyclerView,
              RecyclerView.ViewHolder viewHolder,
              RecyclerView.ViewHolder target) {
            return false;
          }

          @Override
          public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            ViewPropertyAnimator animator = viewHolder.itemView.animate();
            animator.translationYBy(1000).setDuration(1000).start();
            filelist.removeItemWithAnimation(position,file);
          }

          @Override
          public void onSelectedChanged(
              @Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
              viewHolder
                  .itemView
                  .animate()
                  .alpha(0)
                  .setDuration(1000)
                  .withEndAction(
                      () -> {
                        viewHolder.itemView.animate().alpha(1).setDuration(1000).start();
                      })
                  .start();
            }
          }

          @Override
          public void clearView(
              @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
          }
        };

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
    itemTouchHelper.attachToRecyclerView(binding.rv);
  }
}

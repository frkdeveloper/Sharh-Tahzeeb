package naveed.com.sharhtahzeeb.activities;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import naveed.com.sharhtahzeeb.R;
import naveed.com.sharhtahzeeb.adapters.BooksAdapter;
import naveed.com.sharhtahzeeb.listeners.RecyclerItemClickListener;
import naveed.com.sharhtahzeeb.model.BookModel;
import naveed.com.sharhtahzeeb.utils.Utils;

public class ListActivity extends ActionBarActivity {

    private Context mContext;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private String[] booksAssetsStr = new String[]{"SharhTahzeebAlBushra.pdf"};
    private String[] volumesStr = new String[]{""};
    private int[] images = new int[]{R.drawable.app_icon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializers

        mContext = this;
        List<BookModel> items = new ArrayList<>();
        for (int i = 0; i < booksAssetsStr.length; i++) {
            items.add(new BookModel(images[i], getString(R.string.app_name), volumesStr[i]));
        }

        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new BooksAdapter(items);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        CopyReadAssets(booksAssetsStr[position]);
                    }
                })
        );
    }


    private void CopyReadAssets(String fileName) {
        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                in = assetManager.open(fileName);
                out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
        }

        if(Utils.checkAppExists(mContext)){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Intent intentChooser = Intent.createChooser(intent, "Choose Pdf Application");
            try {
                startActivity(intentChooser);
            } catch (ActivityNotFoundException e) {
                //com.adobe.reader
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.adobe.reader")));
            }
        }else{
            //com.adobe.reader
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.adobe.reader")));
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionShare:
                if(Utils.isNetworkAvailable(mContext))
                showDialog();
                else
                    Utils.showToast(mContext,getString(R.string.no_internet));

        }
        return true;
    }

    private void showDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        Button shareButton = (Button) dialog.findViewById(R.id.btnShare);
        // if button is clicked, close the custom dialog
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Let me recommend you this application");
                String packageName = getString(R.string.package_name);
                String sharelink = getString(R.string.app_link);
                share.putExtra(Intent.EXTRA_TEXT, sharelink + packageName);
                startActivity(Intent.createChooser(share, "Share link!"));
                dialog.dismiss();
            }
        });


        Button rateButton = (Button) dialog.findViewById(R.id.btnRate);
        // if button is clicked, close the custom dialog
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = getString(R.string.package_name);
                Uri uri = Uri.parse("market://details?id=" + packageName);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    Utils.showToast(mContext, "Couldn't launch the market");
                }
                dialog.dismiss();
            }
        });

        Button moreAppsButton = (Button) dialog.findViewById(R.id.btnMoreApps);
        // if button is clicked, close the custom dialog
        moreAppsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountLink = getString(R.string.account_link);
                Uri uri = Uri.parse(accountLink);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    Utils.showToast(mContext, "Couldn't launch the market");
                }
                dialog.dismiss();
            }
        });


        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        new Handler().postDelayed(new Runnable() {
            public void run() {
                dialog.show();
            }
        }, 100);


        dialog.show();
    }

}

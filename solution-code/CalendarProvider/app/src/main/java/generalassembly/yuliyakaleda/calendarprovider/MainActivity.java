package generalassembly.yuliyakaleda.calendarprovider;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
  private static final String TAG = "MainActivity";
  private static final int CALENDAR_LOADER = 0;
  private RecyclerView mRecyclerView;
  private CalendarRecylerViewAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mRecyclerView = (RecyclerView) findViewById(R.id.calendar_list);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    mAdapter = new CalendarRecylerViewAdapter(new ArrayList<CalendarEvent>());
    mRecyclerView.setAdapter(mAdapter);

    getSupportLoaderManager().initLoader(CALENDAR_LOADER,null,this);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    switch (id){
      case CALENDAR_LOADER:
        return new CursorLoader(
                this,
                CalendarContract.Events.CONTENT_URI,
                null,
                null,
                null,
                CalendarContract.Events.DTSTART+" DESC");
      default:
        return null;
    }
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mAdapter.swapData(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    mAdapter.swapData(null);
  }
}



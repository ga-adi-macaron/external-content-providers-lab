package generalassembly.yuliyakaleda.calendarprovider;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by drewmahrt on 11/22/16.
 */

public class CalendarRecylerViewAdapter extends RecyclerView.Adapter<CalendarRecylerViewAdapter.CalendarViewHolder>{
    private static final String TAG = "CalendarRecylerViewAdap";
    public static final Uri CONTENT_URI = CalendarContract.Events.CONTENT_URI;

    List<CalendarEvent> mCalendarEventList;

    public CalendarRecylerViewAdapter(List<CalendarEvent> calendarEventList) {
        mCalendarEventList = calendarEventList;
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CalendarViewHolder(inflater.inflate(R.layout.calendar_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final CalendarViewHolder holder, int position) {
        holder.mCalendarEventTitle.setText(mCalendarEventList.get(position).getTitle());

        String startTime = DateFormat.getDateInstance(DateFormat.SHORT).format(mCalendarEventList.get(position).getDate());
        holder.mStartTime.setText(startTime);

        holder.mItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mItemContainer.getContext());
                builder.setMessage("Are you sure you want to delete this event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                long eventId = mCalendarEventList.get(holder.getAdapterPosition()).getId();
                                Log.d(TAG, "onClick: Deleting id: "+eventId);
                                Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
                                Log.d(TAG,"Deleting uri: "+uri.toString());
                                holder.mItemContainer.getContext().getContentResolver().delete(uri, null, null);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCalendarEventList.size();
    }

    public void swapData(Cursor cursor){
        mCalendarEventList.clear();

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
                String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
                long startTime = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                mCalendarEventList.add(new CalendarEvent(id,title,startTime));
                cursor.moveToNext();
            }
        }

        Log.d(TAG, "swapData: ");
        notifyDataSetChanged();
    }


    public class CalendarViewHolder extends RecyclerView.ViewHolder{
        public TwoLineListItem mItemContainer;
        public TextView mCalendarEventTitle, mStartTime;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            mItemContainer = (TwoLineListItem)itemView.findViewById(R.id.item_container);
            mCalendarEventTitle = (TextView)itemView.findViewById(R.id.text1);
            mStartTime = (TextView)itemView.findViewById(R.id.text2);
        }
    }
}

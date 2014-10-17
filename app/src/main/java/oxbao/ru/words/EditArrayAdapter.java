package oxbao.ru.words;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

public class EditArrayAdapter extends ArrayAdapter<Word> {
    private Context context;
    private List<Word> values;
    private   SqliteWordHelper db = new SqliteWordHelper(getContext());


    public EditArrayAdapter(Context context, List<Word> objects) {
        super(context, R.layout.edit_adapter, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.edit_adapter, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(values.get(position).toString());

        return rowView;
    }


}

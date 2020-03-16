package info.batson.coronacases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Titus Batson.
 * https://titusbatson.com/
 * 16 - March - 2020
 */

public class CasesAdapter extends RecyclerView.Adapter<CasesAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Cases> casesList;
    private List<Cases> casesListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone, recovered,critical,deaths, todayCases;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);

            recovered = view.findViewById(R.id.recovered);
            critical = view.findViewById(R.id.critical);
            deaths = view.findViewById(R.id.deaths);
            todayCases = view.findViewById(R.id.todayCases);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(casesListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public CasesAdapter(Context context, List<Cases> casesList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.casesList = casesList;
        this.casesListFiltered = casesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Cases cases = casesListFiltered.get(position);
        holder.name.setText(cases.getName());
        holder.phone.setText(cases.getPhone());
        holder.recovered.setText(cases.getrecovered());
        holder.critical.setText(cases.getcritical());
       holder.deaths.setText(cases.getdeaths());
        holder.todayCases.setText(cases.gettodayCases());



    }

    @Override
    public int getItemCount() {
        return casesListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    casesListFiltered = casesList;
                } else {
                    List<Cases> filteredList = new ArrayList<>();
                    for (Cases row : casesList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    casesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = casesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                casesListFiltered = (ArrayList<Cases>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Cases cases);
    }
}

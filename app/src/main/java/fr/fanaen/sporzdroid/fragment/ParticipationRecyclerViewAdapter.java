package fr.fanaen.sporzdroid.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.fanaen.sporzdroid.R;
import fr.fanaen.sporzdroid.model.Participation;
import fr.fanaen.sporzdroid.fragment.ParticipationFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Participation} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ParticipationRecyclerViewAdapter extends RecyclerView.Adapter<ParticipationRecyclerViewAdapter.ViewHolder> {

    private List<Participation> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ParticipationRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
        populate();
    }

    public void populate() {
        mValues = Participation.listAll(Participation.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_participation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Participation item = mValues.get(position);
        holder.bind(item);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onParticipationListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Participation mItem;
        @Bind(R.id.id)      TextView mIdView;
        @Bind(R.id.content) TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        public void bind(Participation participation) {
            mItem = participation;
            mIdView.setText("#" + participation.getId());
            mContentView.setText("Game " + participation.getId());
        }
    }
}

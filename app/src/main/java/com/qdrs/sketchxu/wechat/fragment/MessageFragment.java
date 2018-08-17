package com.qdrs.sketchxu.wechat.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.qdrs.sketchxu.wechat.R;
import com.qdrs.sketchxu.wechat.data.UserMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnMessageFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private MessageAdapter mMessageAdapter;

    private List<UserMessage> messageList = new ArrayList<>();

    private Context mContext;

    private OnMessageFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_message, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview);
        mMessageAdapter = new MessageAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mMessageAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // Inflate the layout for this fragment
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMessageFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnMessageFragmentInteractionListener) {
            mListener = (OnMessageFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMessageFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMessageFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMessageFragmentInteraction(Uri uri);
    }

    class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {


        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            MessageViewHolder holder = new MessageViewHolder(
                    LayoutInflater.from(mContext).inflate(R.layout.layout_message_item, viewGroup, false));
            return null;
        }

        @Override
        public void onBindViewHolder(MessageViewHolder messageViewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class MessageViewHolder extends RecyclerView.ViewHolder {

            private ImageView pic;
            private TextView tvUsername;
            private TextView tvCurrentMsg;

            public MessageViewHolder(View view) {
                super(view);

                pic = (ImageView) view.findViewById(R.id.user_pic);
                tvUsername = (TextView) view.findViewById(R.id.user_name);
                tvCurrentMsg = (TextView) view.findViewById(R.id.curren_msg);
            }
        }
    }
}

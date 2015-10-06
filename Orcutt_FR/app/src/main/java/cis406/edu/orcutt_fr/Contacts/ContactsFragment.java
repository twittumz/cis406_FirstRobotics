package cis406.edu.orcutt_fr.Contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cis406.edu.orcutt_fr.Database.DatabaseHelper;
import cis406.edu.orcutt_fr.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected SQLiteDatabase db;
    protected Cursor cursor;
    protected ContactListAdapter adapter;
    protected ListView contactList;
    private  ArrayList<Contact> contacts;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
     //   args.putString(ARG_PARAM1, param1);
     //   args.putString(ARG_PARAM2, param2);
      //  fragment.setArguments(args);
        return fragment;
    }

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = (new DatabaseHelper(getContext()).getWritableDatabase());

        //query db
        cursor = db.query("FR_contacts",null,null,null,null,null,"firstName ASC");
        contacts = new ArrayList<Contact>();
        cursor.moveToFirst();
        //add each item to the contacts list
        for(int i=0;i<cursor.getCount();i++){
            contacts.add(new Contact(cursor.getInt(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("firstName")),
                    cursor.getString(cursor.getColumnIndex("lastName")), cursor.getString(cursor.getColumnIndex("cellPhone"))));
            cursor.moveToNext();
        }
        cursor.close();
        //set up the adapter
        adapter = new ContactListAdapter(getContext(),R.layout.contact_list_item,contacts);

    }
@Override
public void onStart(){
    super.onStart();
    //query db
    cursor = db.query("FR_contacts",null,null,null,null,null,"firstName ASC");
    contacts = new ArrayList<Contact>();
    cursor.moveToFirst();
    //add each item to the contacts list
    for(int i=0;i<cursor.getCount();i++){
        contacts.add(new Contact(cursor.getInt(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("firstName")),
                cursor.getString(cursor.getColumnIndex("lastName")), cursor.getString(cursor.getColumnIndex("cellPhone"))));
        cursor.moveToNext();
    }
    cursor.close();
    //set up the adapter
    adapter = new ContactListAdapter(getContext(),R.layout.contact_list_item,contacts);

}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_contacts, container, false);
        contactList= (ListView) inf.findViewById(R.id.contacts_listView);
        contactList.setAdapter(adapter);
        // click listener for items
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //show new EditContact activity
                Intent intent = new Intent(getContext(),ViewContact.class);
                // pass in ID pram
                intent.putExtra("Contact_id",contacts.get(position).getId());
                //start the activity
                startActivity(intent);
            }
        });




        return inf;



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
      /*  try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

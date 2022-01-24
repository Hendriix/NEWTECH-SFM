package com.newtech.newtech_sfm.Fragement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Configuration.VisibiliteLigneViewModel;
import com.newtech.newtech_sfm.Fragement.ReferencementFragment.OnListFragmentInteractionListener;
import com.newtech.newtech_sfm.Fragement.dummy.DummyContent.DummyItem;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.VisibiliteLigne;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyReferencementRecyclerViewAdapter extends RecyclerView.Adapter<MyReferencementRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "Referencement";
    private final ArrayList<VisibiliteLigne> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;
    private final Fragment fragment;
    ArticleManager articleManager;
    private final VisibiliteLigneViewModel visibiliteLigneViewModel;

    public MyReferencementRecyclerViewAdapter(ArrayList<VisibiliteLigne> items, OnListFragmentInteractionListener listener, Context context, Fragment fragment) {

        mValues = items;
        mListener = listener;
        this.context =context;
        this.fragment = fragment;
        Log.d(TAG, "onCreateView: activity "+fragment.getActivity().toString());
        visibiliteLigneViewModel = ViewModelProviders.of(fragment.getActivity()).get(VisibiliteLigneViewModel.class);
        visibiliteLigneViewModel.setSelectedVisibiliteLignes(mValues);
        articleManager = new ArticleManager(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_referencement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //holder.visibiliteLigne = mValues.get(position);

        Article article = articleManager.get(mValues.get(position).getARTICLE_CODE());

        holder.article_code.setText(mValues.get(position).getARTICLE_CODE());
        holder.article_designation.setText(article.getARTICLE_DESIGNATION1());
        holder.checkBox.setChecked(getBooleanFromInt(mValues.get(position).getREFERENCEMENT()));
        holder.checkBox2.setChecked(getBooleanFromInt(mValues.get(position).getVISIBILITE()));

        String base64Image = "";

        if(article.getIMAGE().toString().contains(",")){
            base64Image = String.valueOf(article.getIMAGE()).split(",")[1];
        }else{
            base64Image=article.getIMAGE();
        }
        if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){
            if(getImageId(context, article.getARTICLE_CODE().toLowerCase())>0){
                holder.article_image.setImageResource(getImageId(context, article.getARTICLE_CODE().toLowerCase()));
                holder.image_existe.setImageResource(getImageId(context, article.getARTICLE_CODE().toLowerCase()));
            }else{
                holder.article_image.setImageResource(getImageId(context,"bouteille_inconnu2"));
                holder.image_existe.setImageResource(getImageId(context,"bouteille_inconnu2"));
            }
        }else{
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            holder.article_image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
            holder.image_existe.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) holder.checkBox.getTag();
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(mValues.get(position));
                }
            }
        });

        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer positon = (Integer) holder.checkBox.getTag();
                //Log.d(TAG, "onClick: "+ mValues.toString());

                if (getBooleanFromInt(mValues.get(position).getREFERENCEMENT())) {
                    mValues.get(position).setREFERENCEMENT(0);
                    mValues.get(position).setVISIBILITE(0);
                    holder.checkBox2.setChecked(false);
                    //holder.checkBox.setText("NOK");
                } else {
                    mValues.get(position).setREFERENCEMENT(1);
                    //holder.checkBox.setText("OK");
                }
                visibiliteLigneViewModel.setSelectedVisibiliteLignes(mValues);

                Log.d(TAG, "onClick: 1"+ mValues.toString());
            }
        });

        holder.checkBox2.setTag(position);
        holder.checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer positon = (Integer) holder.checkBox2.getTag();
                //Log.d(TAG, "onClick: "+ mValues.toString());


                if (getBooleanFromInt(mValues.get(position).getVISIBILITE())) {

                    mValues.get(position).setVISIBILITE(0);
                    //holder.checkBox2.setText("NOK");
                    if(getBooleanFromInt(mValues.get(position).getREFERENCEMENT())){
                        holder.checkBox.setChecked(false);
                        //holder.checkBox.setText("NOK");
                        mValues.get(position).setREFERENCEMENT(0);
                    }
                } else {

                    holder.checkBox.setChecked(true);
                    //holder.checkBox2.setText("OK");
                    //holder.checkBox.setText("OK");
                    mValues.get(position).setVISIBILITE(1);
                    mValues.get(position).setREFERENCEMENT(1);
                }
                visibiliteLigneViewModel.setSelectedVisibiliteLignes(mValues);

                Log.d(TAG, "onClick: 2"+ mValues.toString());
            }
        });


    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView article_code = null;
        TextView article_designation = null;
        ImageView article_image = null;
        protected CheckBox checkBox;
        protected CheckBox checkBox2;
        ImageView image_existe;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            article_code = (TextView) view.findViewById(R.id.article_code);
            article_designation = (TextView) view.findViewById(R.id.article_designation);
            article_image = (ImageView) view.findViewById(R.id.image_article);
            checkBox =(CheckBox) view.findViewById(R.id.checkBox);
            checkBox2 =(CheckBox) view.findViewById(R.id.checkBox2);
            image_existe = (ImageView) view.findViewById(R.id.image_existe);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public boolean getBooleanFromInt(int value){
        boolean checked = false;

        if(value == 1){
            checked = true;
        }

        return checked;
    }
}

package com.ibao.premescla.ui.orden.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.premescla.R;
import com.ibao.premescla.models.Tancada;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RViewAdapterListTancadas
        extends RecyclerView.Adapter<RViewAdapterListTancadas.ViewHolder>
        implements View.OnClickListener{

    private List<Tancada> tancadaVOList;
    private int cantOD;

    private Context ctx;

    private View.OnClickListener onClickListener;

    private String TAG = RViewAdapterListTancadas.class.getSimpleName();

    public RViewAdapterListTancadas(Context ctx, List<Tancada> list,int cantOD) {

        this.tancadaVOList = new ArrayList<>();
                this.tancadaVOList.addAll(list);
        this.ctx = ctx;
        this.cantOD = cantOD;

    }

    public Tancada getTancada (int index){
        return tancadaVOList.get(index);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tancada_item,null,false);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tancada  item = getTancada(position);
        holder.tancada_item_tViewPosition.setText(""+item.getNroTancada());
        holder.tancada_item_tViewPPAll.setText(""+cantOD);
        holder.tancada_item_tViewPPCount.setText(""+item.getProductosPesados().size());

    }

    public void setOnClicListener(View.OnClickListener listener){
        this.onClickListener=listener;

    }

    @Override
    public void onClick(View v) {
        if(onClickListener!=null){
            onClickListener.onClick(v);
        }
    }

    @Override
    public int getItemCount() {
        return tancadaVOList.size();
    }

    private Date getHourFromDate(String dateString){

        Date date = null;
        try {
            date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tancada_item_tViewPosition;
        TextView tancada_item_tViewPPAll;
        TextView tancada_item_tViewPPCount;
        TextView fmain_item_nTankAll;
        TextView fmain_item_dateTime;

        FloatingActionButton tareo_item_fab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tancada_item_tViewPosition = itemView.findViewById(R.id.tancada_item_tViewPosition);
            tancada_item_tViewPPAll = itemView.findViewById(R.id.tancada_item_tViewPPAll);
            tancada_item_tViewPPCount = itemView.findViewById(R.id.tancada_item_tViewPPCount);
/*
            fmain_item_Fundo = itemView.findViewById(R.id.fmain_item_Fundo);
            fmain_item_Empresa = itemView.findViewById(R.id.fmain_item_Empresa);
            fmain_item_nTankAll = itemView.findViewById(R.id.fmain_item_nTankAll);
            fmain_item_dateTime = itemView.findViewById(R.id.fmain_item_dateTime);
*/
        }
    }
}

package com.ibao.premescla.ui.tancada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.premescla.R;
import com.ibao.premescla.models.ProductoPesado;
import com.ibao.premescla.models.Tancada;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RViewAdapterListProductoPesado
        extends RecyclerView.Adapter<RViewAdapterListProductoPesado.ViewHolder>
        implements View.OnClickListener{

    private List<ProductoPesado> tancadaVOList;


    private Context ctx;

    private View.OnClickListener onClickListener;

    private String TAG = RViewAdapterListProductoPesado.class.getSimpleName();

    public RViewAdapterListProductoPesado(Context ctx, List<ProductoPesado> list) {

        this.tancadaVOList = new ArrayList<>();
                this.tancadaVOList.addAll(list);
        this.ctx = ctx;

    }

    public ProductoPesado getProductoPesado (int index){
        return tancadaVOList.get(index);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ppesado_item,null,false);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductoPesado  item = getProductoPesado(position);

        holder.ppesado_item_tViewPosition.setText(""+(position+1));
        holder.ppesado_item_tViewPeso.setText(""+item.getCantidadPesada());
        holder.ppesado_item_tViewProductName.setText(""+item.getProductName());
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



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ppesado_item_tViewPosition;
        TextView ppesado_item_tViewPeso;
        TextView ppesado_item_tViewProductName;
        //TextView tancada_item_tViewPPAll;
        //TextView tancada_item_tViewPPCount;
        //TextView fmain_item_nTankAll;
        //TextView fmain_item_dateTime;

        FloatingActionButton tareo_item_fab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ppesado_item_tViewPosition = itemView.findViewById(R.id.ppesado_item_tViewPosition);
            ppesado_item_tViewPeso = itemView.findViewById(R.id.ppesado_item_tViewPeso);
            ppesado_item_tViewProductName = itemView.findViewById(R.id.ppesado_item_tViewProductName);
          //  tancada_item_tViewPPAll = itemView.findViewById(R.id.tancada_item_tViewPPAll);
          //  tancada_item_tViewPPCount = itemView.findViewById(R.id.tancada_item_tViewPPCount);
/*
            fmain_item_Fundo = itemView.findViewById(R.id.fmain_item_Fundo);
            fmain_item_Empresa = itemView.findViewById(R.id.fmain_item_Empresa);
            fmain_item_nTankAll = itemView.findViewById(R.id.fmain_item_nTankAll);
            fmain_item_dateTime = itemView.findViewById(R.id.fmain_item_dateTime);
*/
        }
    }
}

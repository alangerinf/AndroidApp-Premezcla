package com.ibao.premezcla.ui.mod1.views.fragment.tancada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibao.premezcla.R;
import com.ibao.premezcla.models.ProductoPesado;

import java.util.ArrayList;
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
                    .inflate(R.layout.mod1_frag_tancada_item_ppesado,null,false);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ppesado_item_tViewPosition = itemView.findViewById(R.id.ppesado_item_tViewPosition);
            ppesado_item_tViewPeso = itemView.findViewById(R.id.ppesado_item_tViewPeso);
            ppesado_item_tViewProductName = itemView.findViewById(R.id.ppesado_item_tViewProductName);

        }
    }
}

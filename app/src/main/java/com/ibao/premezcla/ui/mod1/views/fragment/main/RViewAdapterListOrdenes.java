package com.ibao.premezcla.ui.mod1.views.fragment.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.premezcla.R;
import com.ibao.premezcla.models.Orden;
import java.util.ArrayList;
import java.util.List;

import static com.ibao.premezcla.models.Orden.status_enproceso;
import static com.ibao.premezcla.models.Orden.status_finalizada;
import static com.ibao.premezcla.models.Orden.status_pendiente;


public class RViewAdapterListOrdenes
        extends RecyclerView.Adapter<RViewAdapterListOrdenes.ViewHolder>
        implements View.OnClickListener{

    private List<Orden> tareoDetalleVOList;

    private Context ctx;

    private View.OnClickListener onClickListener;

    private String TAG = RViewAdapterListOrdenes.class.getSimpleName();

    public RViewAdapterListOrdenes(Context ctx, List<Orden> ordenList) {

        this.tareoDetalleVOList = new ArrayList<>();
                this.tareoDetalleVOList.addAll(ordenList);
        this.ctx = ctx;

    }

    public Orden getOrden (int index){
        return tareoDetalleVOList.get(index);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mod1_frag_main_item_orden,null,false);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orden  item = getOrden(position);
        holder.fmain_item_Fundo.setText(""+item.getCultivoName()+" / "+item.getLoteCode());
        holder.fmain_item_Empresa.setText(""+item.getFundoName());
        holder.fmain_item_dateTime.setText(""+item.getAplicacionDate());
        holder.fmain_item_nOrden.setText(""+item.getOrdenCode());
        holder.fmain_item_nTankAll.setText(""+item.getTancadasProgramadas());
        holder.fmain_item_nTankComplete.setText(""+item.getCantComplete());

        holder.fmain_item_pendiente.setVisibility(View.GONE);
        holder.fmain_item_finish.setVisibility(View.GONE);
        holder.fmain_item_onprocess.setVisibility(View.GONE);
        switch (item.getCurrentProccess()){
            case status_pendiente:
                holder.fmain_item_pendiente.setVisibility(View.VISIBLE);
                holder.fmain_item_onprocess.setVisibility(View.GONE);
                holder.fmain_item_finish.setVisibility(View.GONE);
                break;
            case status_enproceso:
                holder.fmain_item_pendiente.setVisibility(View.GONE);
                holder.fmain_item_onprocess.setVisibility(View.VISIBLE);
                holder.fmain_item_finish.setVisibility(View.GONE);
                break;
            case status_finalizada:
                holder.fmain_item_pendiente.setVisibility(View.GONE);
                holder.fmain_item_onprocess.setVisibility(View.GONE);
                holder.fmain_item_finish.setVisibility(View.VISIBLE);
                break;

        }
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
        return tareoDetalleVOList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fmain_item_nOrden;
        TextView fmain_item_Fundo;
        TextView fmain_item_Empresa;
        TextView fmain_item_nTankAll;
        TextView fmain_item_nTankComplete;
        TextView fmain_item_dateTime;
        LinearLayout fmain_item_finish;
        LinearLayout fmain_item_onprocess;
        LinearLayout fmain_item_pendiente;
        FloatingActionButton tareo_item_fab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fmain_item_pendiente = itemView.findViewById(R.id.fmain_item_pendiente);
            fmain_item_onprocess = itemView.findViewById(R.id.fmain_item_onprocess);
            fmain_item_finish = itemView.findViewById(R.id.fmain_item_finish);
            fmain_item_nOrden = itemView.findViewById(R.id.fmain_item_nOrden);
            fmain_item_Fundo = itemView.findViewById(R.id.fmain_item_Fundo);
            fmain_item_Empresa = itemView.findViewById(R.id.fmain_item_Empresa);
            fmain_item_nTankComplete = itemView.findViewById(R.id.fmain_item_nTankComplete);
            fmain_item_nTankAll = itemView.findViewById(R.id.fmain_item_nTankAll);
            fmain_item_dateTime = itemView.findViewById(R.id.fmain_item_dateTime);

        }
    }
}

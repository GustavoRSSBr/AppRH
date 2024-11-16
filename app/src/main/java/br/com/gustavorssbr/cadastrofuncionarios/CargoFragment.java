package br.com.gustavorssbr.cadastrofuncionarios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.com.gustavorssbr.cadastrofuncionarios.controller.CargoController;
import br.com.gustavorssbr.cadastrofuncionarios.model.Cargo;
import br.com.gustavorssbr.cadastrofuncionarios.persistence.CargoDao;

public class CargoFragment extends Fragment {
    private View view;

    private EditText etCodigoCargo, etNomeCargo;

    private TextView tvListarCargo, tvTituloCargo;

    private Button btnListarCargo, btnInserirCargo, btnExcluirCargo, btnModificarCargo, btnBuscarCargo;

    private CargoController cCount;

    public CargoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cargo, container, false);
        etCodigoCargo = view.findViewById(R.id.etCodigoCargo);
        etNomeCargo = view.findViewById(R.id.etNomeCargo);
        tvListarCargo = view.findViewById(R.id.tvListarCargo);
        tvListarCargo.setMovementMethod(new ScrollingMovementMethod());
        tvTituloCargo = view.findViewById(R.id.tvTituloCargo);
        btnListarCargo = view.findViewById(R.id.btnListarCargo);
        btnInserirCargo = view.findViewById(R.id.btnInserirCargo);
        btnExcluirCargo = view.findViewById(R.id.btnExcluirCargo);
        btnModificarCargo = view.findViewById(R.id.btnModificarCargo);
        btnBuscarCargo = view.findViewById(R.id.btnBuscarCargo);

        btnInserirCargo.setOnClickListener(op -> acaoInserirCargo());
        btnModificarCargo.setOnClickListener(op -> acaoModificarCargo());
        btnExcluirCargo.setOnClickListener(op -> acaoExcluirCargo());
        btnBuscarCargo.setOnClickListener(op -> acaoBuscarCargo());
        btnListarCargo.setOnClickListener(op -> acaoListarCargo());

        cCount = new CargoController(new CargoDao(view.getContext()));

        return  view;
    }

    private void acaoInserirCargo() {
        Cargo cargo = montaCargo();
        try {
            cCount.insert(cargo);
            Toast.makeText(view.getContext(), "Cargo inserido com sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoModificarCargo() {
        Cargo cargo = montaCargo();
        try {
            cCount.update(cargo);
            Toast.makeText(view.getContext(), "Cargo alterado com sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoExcluirCargo() {
        Cargo cargo = montaCargo();
        try {
            cCount.delete(cargo);
            Toast.makeText(view.getContext(), "Cargo excluido com sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscarCargo() {
        Cargo cargo = montaCargo();

        try {
            cargo = cCount.findOne(cargo);
            if(cargo.getNome() != null){
                preecheCampos(cargo);
            } else {
                Toast.makeText(view.getContext(), "Cargo não encontrado!",
                        Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void acaoListarCargo() {
        try {
            List<Cargo> cargos = cCount.findAll();
            StringBuffer buffer = new StringBuffer();

            for(Cargo c : cargos){
                buffer.append(c.toString() + "\n");
            }

            tvListarCargo.setText(buffer.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private Cargo montaCargo(){
        Cargo cargo = new Cargo();

        cargo.setCodigo(Integer.parseInt(etCodigoCargo.getText().toString()));
        cargo.setNome(etNomeCargo.getText().toString());

        return cargo;
    }

    private void preecheCampos(Cargo cargo) {
        etCodigoCargo.setText(String.valueOf(cargo.getCodigo()));
        etNomeCargo.setText(cargo.getNome());
    }

    private void limpaCampos(){
        etNomeCargo.setText("");
        etCodigoCargo.setText("");
    }

}
package br.com.gustavorssbr.cadastrofuncionarios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.com.gustavorssbr.cadastrofuncionarios.controller.CargoController;
import br.com.gustavorssbr.cadastrofuncionarios.controller.FuncionarioController;
import br.com.gustavorssbr.cadastrofuncionarios.model.Cargo;
import br.com.gustavorssbr.cadastrofuncionarios.model.Clt;
import br.com.gustavorssbr.cadastrofuncionarios.model.Funcionario;
import br.com.gustavorssbr.cadastrofuncionarios.model.Pj;
import br.com.gustavorssbr.cadastrofuncionarios.persistence.CargoDao;
import br.com.gustavorssbr.cadastrofuncionarios.persistence.FuncionarioDao;


public class FuncionarioFragment extends Fragment {
    private View view;

    TextView tvTituloFunc, tvListarFunc;

    EditText etCodigoFunc, etDocumentoFunc, etSalarioFunc, etNomeFunc;

    Button btnInserirFunc, btnBuscarFunc, btnModificarFunc, btnExcluirFunc, btnListarFunc;

    Spinner spCargoFunc;

    private FuncionarioController fCont;
    private CargoController cCont;
    private List<Cargo> cargos;

    public FuncionarioFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_funcionario, container, false);
        tvTituloFunc = view.findViewById(R.id.tvTituloFunc);
        tvListarFunc = view.findViewById(R.id.tvListarFunc);
        tvListarFunc.setMovementMethod(new ScrollingMovementMethod());
        etCodigoFunc = view.findViewById(R.id.etCodigoFunc);
        etNomeFunc = view.findViewById(R.id.etNomeFunc);
        etDocumentoFunc = view.findViewById(R.id.etDocumentoFunc);
        etSalarioFunc = view.findViewById(R.id.etSalarioFunc);
        btnInserirFunc = view.findViewById(R.id.btnInserirFunc);
        btnListarFunc = view.findViewById(R.id.btnListarFunc);
        btnExcluirFunc = view.findViewById(R.id.btnExcluirFunc);
        btnModificarFunc = view.findViewById(R.id.btnModificarFunc);
        btnBuscarFunc = view.findViewById(R.id.btnBuscarFunc);
        spCargoFunc = view.findViewById(R.id.spCargoFunc);

        fCont = new FuncionarioController(new FuncionarioDao(view.getContext()));
        cCont = new CargoController(new CargoDao(view.getContext()));
        
        preencheSpinner();

        btnInserirFunc.setOnClickListener(op -> acaoInserirFunc());
        btnBuscarFunc.setOnClickListener(op -> acaoBuscarFunc());
        btnModificarFunc.setOnClickListener(op -> acaoModificarFunc());
        btnExcluirFunc.setOnClickListener(op -> acaoExcluirFunc());
        btnListarFunc.setOnClickListener(op -> acaoListarFunc());

        return view;
    }

    private void preencheSpinner() {
        Cargo c0 = new Cargo();
        c0.setCodigo(0);
        c0.setNome("Selecione um cargo");

        try {
            cargos = cCont.findAll();
            cargos.add(0, c0);

            ArrayAdapter adapter = new ArrayAdapter(view.getContext(),
                    android.R.layout.simple_spinner_item,
                    cargos
            );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCargoFunc.setAdapter(adapter);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoInserirFunc() {
        int selectedItemPosition = spCargoFunc.getSelectedItemPosition();
        if(selectedItemPosition > 0){
            Funcionario funcionario = montaFuncionario();
            try {
                fCont.insert(funcionario);
                Toast.makeText(view.getContext(), "Funcionário inserido com sucesso!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
        } else {
            Toast.makeText(view.getContext(), "Selecione um cargo.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void acaoModificarFunc() {
        int selectedItemPosition = spCargoFunc.getSelectedItemPosition();
        if(selectedItemPosition > 0){
            Funcionario funcionario = montaFuncionario();
            try {
                fCont.update(funcionario);
                Toast.makeText(view.getContext(), "Funcionário atualizado com sucesso!",
                        Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
        } else {
            Toast.makeText(view.getContext(), "Selecione um cargo.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluirFunc() {

            Funcionario funcionario = montaFuncionario();
            try {
                fCont.delete(funcionario);
                Toast.makeText(view.getContext(), "Funcionário deletado com sucesso!",
                        Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
    }

    private void acaoBuscarFunc() {
        Funcionario funcionario = montaFuncionario();
        try {
            cargos = cCont.findAll();
            funcionario = fCont.findOne(funcionario);
            if(funcionario != null && funcionario.getNome() != null){
                preencheCampos(funcionario);
            } else {
                Toast.makeText(view.getContext(), "Funcionário não encontrado!",
                        Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListarFunc() {
        try {
            List<Funcionario> funcionarios = fCont.findAll();
            StringBuffer buffer = new StringBuffer();

            for(Funcionario f : funcionarios){
                buffer.append(f.toString() + "\n");
            }

            tvListarFunc.setText(buffer.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Funcionario montaFuncionario() {
        Funcionario funcionario;
        String documento = etDocumentoFunc.getText().toString().trim();

        if (documento.length() == 11) {
            funcionario = new Clt();
            ((Clt) funcionario).setCpf(documento);
        } else {
            funcionario = new Pj();
            ((Pj) funcionario).setCnpj(documento);
        }

        funcionario.setNome(etNomeFunc.getText().toString().trim());

        String codigoStr = etCodigoFunc.getText().toString().trim();
        if (!codigoStr.isEmpty()) {
            funcionario.setCodigo(Integer.parseInt(codigoStr));
        }

        String salarioStr = etSalarioFunc.getText().toString().trim();
        if (!salarioStr.isEmpty()) {
            funcionario.setSalario(Double.parseDouble(salarioStr));
        }

        Cargo cargo = (Cargo) spCargoFunc.getSelectedItem();
        if (cargo != null) {
            funcionario.setCargo(cargo);
        }

        return funcionario;
    }


    private void preencheCampos(Funcionario funcionario){
        if(funcionario instanceof Pj){
            etDocumentoFunc.setText(((Pj) funcionario).getCnpj() );
        }

        if(funcionario instanceof Clt){
            etDocumentoFunc.setText(((Clt) funcionario).getCpf() );
        }

        etNomeFunc.setText(funcionario.getNome());
        etCodigoFunc.setText(String.valueOf(funcionario.getCodigo()));
        etSalarioFunc.setText(String.valueOf(funcionario.getSalario()));

        int cont = 1;

        for(Cargo c : cargos){
            if(c.getCodigo() == funcionario.getCargo().getCodigo()){
                spCargoFunc.setSelection(cont);
            } else {
                cont++;

            }
        }
        if(cont > cargos.size()){
            spCargoFunc.setSelection(0);
        }
    }

    private void limpaCampos(){
        etDocumentoFunc.setText("");
        etNomeFunc.setText("");
        etCodigoFunc.setText("");
        etSalarioFunc.setText("");
        spCargoFunc.setSelection(0);
    }


}
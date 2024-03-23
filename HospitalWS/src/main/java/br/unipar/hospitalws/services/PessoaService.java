package br.unipar.hospitalws.services;

import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.models.PessoaModel;

public class PessoaService {
    public static void validaPessoa(PessoaModel pessoa) {
        if(pessoa.getCpf().length() != 11) {
            throw new ValidationException("CPF inválido! Informe um CPF com 11 digitos");
        }
        if(pessoa.getNome().length() < 0
                || pessoa.getNome().isEmpty()
                || pessoa.getNome() == null){
            throw new ValidationException("Nome inválido! Porfavor informe algum nome");
        }
        if(pessoa.getGmail().length() < 0
                || pessoa.getGmail().isEmpty()
                || pessoa.getGmail() == null){
            throw new ValidationException("E-mail inválido! Porfavor informe algum e-mail");
        }
        if(pessoa.getTelefone().length() < 0
                || pessoa.getTelefone().length() < 9
                || pessoa.getTelefone().isEmpty()
                || pessoa.getTelefone() == null){
            throw new ValidationException("Telefone inválido! Porfavor informe um telefone válido");
        }
        
        EnderecoService.validaEndereco(pessoa.getEndereco());
    }
    
    
}

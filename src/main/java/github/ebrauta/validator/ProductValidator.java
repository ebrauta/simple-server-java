package github.ebrauta.validator;

import github.ebrauta.exception.ValidationException;
import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;

public class ProductValidator {
    public static void validate(Product product){
        validateName(product.name());
        validatePrice(product.price());
    }
    public static void validatePatch(ProductPatch patch){
        if(patch.getName() != null){
            validateName(patch.getName());
        }
        if(patch.getPrice() != null){
            validatePrice(patch.getPrice());
        }
    }

    private static void validateName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new ValidationException("Nome é obrigatório");
        }
        if(name.length() < 3){
            throw new ValidationException("Nome deve ter pelo menos 3 caracteres");
        }
    }

    private static void validatePrice(double price){
        if(price <= 0){
            throw new ValidationException("Preço deve ser maior que zero");
        }
    }
}

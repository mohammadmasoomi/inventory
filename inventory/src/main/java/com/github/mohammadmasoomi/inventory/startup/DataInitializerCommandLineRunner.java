package com.github.mohammadmasoomi.inventory.startup;

import com.github.mohammadmasoomi.inventory.core.entity.security.Permission;
import com.github.mohammadmasoomi.inventory.core.entity.security.Role;
import com.github.mohammadmasoomi.inventory.core.entity.security.User;
import com.github.mohammadmasoomi.inventory.core.ontology.PermissionOntology;
import com.github.mohammadmasoomi.inventory.core.repository.security.PermissionRepository;
import com.github.mohammadmasoomi.inventory.core.repository.security.RoleRepository;
import com.github.mohammadmasoomi.inventory.core.repository.security.UserRepository;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import com.github.mohammadmasoomi.inventory.stock.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DataInitializerCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StockRepository stockRepository;

    public DataInitializerCommandLineRunner(UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, StockRepository stockRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(String... args) {
        Permission saveStockPermission = createPermission(permissionRepository,
                PermissionOntology.SAVE_STOCK, "SAVE_STOCK");
        Permission deleteStockPermission = createPermission(permissionRepository,
                PermissionOntology.DELETE_STOCK, "DELETE_STOCK");
        Permission getAllStockPermission = createPermission(permissionRepository,
                PermissionOntology.GET_ALL_STOCK, "GET_ALL_STOCK");
        Permission getStockByNamePermission = createPermission(permissionRepository,
                PermissionOntology.GET_SOCK_BY_NAME, "GET_STOCK_BY_NAME");
        Permission updateStockPermission = createPermission(permissionRepository,
                PermissionOntology.UPDATE_STOCK_PRICE, "UPDATE_STOCK_PRICE");

        Role roleManipulate = createRole(roleRepository, "001", "MANIPULATE",
                deleteStockPermission, saveStockPermission, updateStockPermission);
        Role roleReport = createRole(roleRepository, "002", "REPORT",
                getAllStockPermission, getStockByNamePermission);

        createUser(userRepository, passwordEncoder, roleManipulate, "root");
        createUser(userRepository, passwordEncoder, roleReport, "mohammad");
        loadData(stockRepository);
    }

    private Permission createPermission(PermissionRepository permissionRepository, String code, String description) {
        Permission permission = permissionRepository.findPermissionByCode(code);
        if (permission == null) {
            permission = new Permission();
            permission.setCode(code);
            permission.setDescription(description);
            permissionRepository.save(permission);
        }
        return permission;
    }

    private void createUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, Role roleManipulate, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(username));
            user.addRole(roleManipulate);
            userRepository.save(user);
        }
    }

    private Role createRole(RoleRepository roleRepository, String code, String report, Permission... p) {
        Role role = roleRepository.findRoleByCode(code);
        if (role == null) {
            role = new Role();
            role.setName(report);
            role.setCode(code);
            role.setPermissions(new HashSet<>(Arrays.asList(p)));
            roleRepository.save(role);
        }
        return role;
    }

    private void loadData(StockRepository stockRepository) {
        long count = stockRepository.count();
        if (count != 0) {
            return;
        }
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("Mouse", BigDecimal.valueOf(40.80)));
        stocks.add(new Stock("Keyboard", BigDecimal.valueOf(90.11)));
        stocks.add(new Stock("Laptop DELL", BigDecimal.valueOf(980.99)));
        stocks.add(new Stock("Laptop Lenovo", BigDecimal.valueOf(900.99)));
        stocks.add(new Stock("MAC Book", BigDecimal.valueOf(1200.99)));
        stocks.add(new Stock("Book Java", BigDecimal.valueOf(51.99)));
        stocks.add(new Stock("Book C++", BigDecimal.valueOf(44.99)));
        stocks.add(new Stock("Book Kotlin", BigDecimal.valueOf(33.22)));
        stocks.add(new Stock("Book Docker", BigDecimal.valueOf(45.22)));
        stocks.add(new Stock("Crampons", BigDecimal.valueOf(100.10)));
        stocks.add(new Stock("Ice axe", BigDecimal.valueOf(80.22)));
        stocks.add(new Stock("Pulley", BigDecimal.valueOf(12.12)));
        stocks.add(new Stock("Climbing pack", BigDecimal.valueOf(400.11)));
        stocks.add(new Stock("Helmet", BigDecimal.valueOf(30.11)));
        stocks.add(new Stock("Harness", BigDecimal.valueOf(37.77)));
        stockRepository.saveAll(stocks);
    }

}

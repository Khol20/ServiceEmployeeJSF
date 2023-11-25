/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.domaine;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import ma.projet.beans.Employee;
import ma.projet.beans.Service;
import ma.projet.service.EmployeeService;
import ma.projet.service.ServiceService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author LACHGAR
 */
@ManagedBean(name = "serviceBean")
public class ServiceBean {

    private Employee employee;
    private Service service;
    
    private ServiceService serviceService;
    private List<Service> services;
    private List<Employee> employees;
    private EmployeeService employeeService;
    private static ChartModel barModel;

    public ServiceBean() {
        service = new Service();
        serviceService = new ServiceService();
        employee = new Employee();
        employeeService = new EmployeeService();
    }

    public List<Employee> getEmployees() {
        if (employees == null) {
            employees = service.getEmployees();
        }
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Service> getServices() {
        if (services == null) {
            services = serviceService.getAll();
        }
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

   
   
    
    

    public String onCreateAction() {
        serviceService.create(service);
        service  = new Service();
        return null;
    }

    public void onDeleteAction() {
        service.setEmployees(null);
        serviceService.delete(service);

    }

    public void onEdit(RowEditEvent event) {
        service = (Service) event.getObject();
        service.setEmployees(null);
        serviceService.update(service);
    }

    public void load() {
        System.out.println(service.getNom());
        service = serviceService.getById(service.getId());
        getEmployees();
        
    }

    public void onCancel(RowEditEvent event) {
    }

    public void onEditm(RowEditEvent event) {
        employee = (Employee) event.getObject();
        Service service = serviceService.getById(this.employee.getService().getId());
        employee.setService(service);
        employee.getService().setNom(service.getNom());
        employeeService.update(employee);
    }

    public String onDeleteActionm() {

        employeeService.delete(employeeService.getById(employee.getId()));
        return null;
    }

    public List<Employee> serviceLoad() {
        for (Employee m : employeeService.getAll()) {
            if (m.getService().equals(service)) {
                employees.add(m);
            }
        }
        return employees;

    }

    public void onCancelm(RowEditEvent event) {
    }

    public ChartModel getBarModel() {
        return barModel;
    }

    public ChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries services = new ChartSeries();
        services.setLabel("Services");
        model.setAnimate(true);
        for (Service s : serviceService.getAll()) {
            services.set(s.getNom(), s.getEmployees().size());
        }
        model.addSeries(services);
        
                
        return model;
    }
}

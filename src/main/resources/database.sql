


  insert into users values("root",1,"1234","ADMIN");
  insert into admin values(1,"root@gmail.com","root","1234");




insert into vendor values(1,"Pune","9881","shubham@gmail.com","","1234");  
 

admin: 
 {
 "email"   : "root@gmail.com",        
 "name"   : "root",
 "password": "1234" 
 }




vendor: 
{
 "address" : "Pune",  
 " contact" : "8668951369",
  "email" : "amit@gmail.com",           
  "name" : "Amit" ,
  "password" : "1234"
}



employeee: 
 {        
 "birth_date" : "1994-01-01"  
 "email" : "ram@gmail.com"       
 "name"   : "Ram"     
 "password" : "1234"   
 "vendor_id" : 1      

 }

 customer:
 {
"birth_date" : "1994-01-01" , 
 "email" : "sham@gmail.com",       
 "name"   : "Sham",     
 "password" : "1234",   
  "address" : "Mumbai",  
 " contact" : "8668951369" 
 }


service request: 
{
 "request_id" : ,     
 "customer_id" : ,     
 "delivery_type": "DROPBY" ,  
 "discount"     :   , 
 "labour_charges" : , 
 "out_date"      : "", 
 "service_date"  : "", 
 "status"        : "", 
 "total"         : "", 
 "vehicle_brand"  : "", 
 "vehicle_model"  : " ",
 "vehicle_reg_no" : "",
 "vehicle_type"   : ""
}

while requesting service
{   
 "customer_id" : 1,     
 "delivery_type": "DROPBY" ,    
 "vehicle_brand"  : "Tata", 
 "vehicle_model"  : "Indica",
 "vehicle_reg_no" : "MH 12 1234",
 "vehicle_type"   : "CAR"
}


{
         
 "discount"     :  10 , 
 "labour_charges" :1000 , 
 "out_date"      : "2020-01-20", 
 "status"        : "COMPLETED", 
 "total"         : discount + labour_charges
 
}

stock 
{
 "stock_id" : ,  
 "item_name" : "", 
 "price"  : ,   
 "quantity" :   
}


{
   
 "item_name" : "gear", 
 "price"  : 1000,   
 "quantity" : 2  
}
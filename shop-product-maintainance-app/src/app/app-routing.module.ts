import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateProductComponent } from './Components/create-product/create-product.component';
import { ProductDetailsComponent } from './Components/product-details/product-details.component';
import { ProductListComponent } from './Components/product-list/product-list.component';
import { UpdateProductComponent } from './Components/update-product/update-product.component';

const routes: Routes = [ 
{ path: '', redirectTo: 'add', pathMatch: 'full' },
{ path: 'products', component: ProductListComponent },
{ path: 'add', component: CreateProductComponent },
{ path: 'update/:id', component: UpdateProductComponent },
{ path: 'details/:id', component: ProductDetailsComponent },];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

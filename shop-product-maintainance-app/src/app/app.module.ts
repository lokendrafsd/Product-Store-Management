import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateProductComponent } from './Components/create-product/create-product.component';
import { UpdateProductComponent } from './Components/update-product/update-product.component';
import { ProductListComponent } from './Components/product-list/product-list.component';
import { ProductDetailsComponent } from './Components/product-details/product-details.component';
import { ProductSearchPipe } from './Pipes/product-search.pipe';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';



@NgModule({
  declarations: [
    AppComponent,
    CreateProductComponent,
    UpdateProductComponent,
    ProductListComponent,
    ProductDetailsComponent,
    ProductSearchPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

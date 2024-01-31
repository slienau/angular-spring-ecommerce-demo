import { Injectable } from '@angular/core';
import { CartItem } from '../common/cart-item';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cartItems: CartItem[] = [];

  // see https://www.learnrxjs.io/learn-rxjs/subjects for the different 'Observable' types
  // totalPrice: Subject<number> = new Subject<number>(); // Subject: No initial value or replay behavior.
  // totalQuantity: Subject<number> = new Subject<number>();

  totalPrice: Subject<number> = new BehaviorSubject<number>(0); // BehaviorSubject: Requires an initial value and emits its current value (last emitted item) to new subscribers.
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);

  constructor() {}

  addToCart(theCartItem: CartItem) {
    // check if we already have the item in our cart
    let existingCartItem: CartItem | undefined;

    if (this.cartItems.length > 0) {
      // find the item in the cart based on item id
      existingCartItem = this.cartItems.find(
        (item) => item.id === theCartItem.id,
      );
    }

    if (existingCartItem != undefined) {
      existingCartItem.quantity++;
    } else {
      this.cartItems.push(theCartItem);
    }

    // compute total price and total quantity
    this.computeCartTotals();
  }

  decrementQuantity(cartItem: CartItem) {
    cartItem.quantity--;

    if (cartItem.quantity === 0) {
      this.remove(cartItem);
    } else {
      this.computeCartTotals();
    }
  }

  remove(cartItem: CartItem) {
    // get index of item in the array
    const itemIndex = this.cartItems.findIndex(
      (tempCartItem) => tempCartItem.id === cartItem.id,
    );
    if (itemIndex > -1) {
      this.cartItems.splice(itemIndex, 1);

      this.computeCartTotals();
    }
  }

  computeCartTotals() {
    let nextPriceValue: number = 0;
    let nextQuantityValue: number = 0;

    for (const currentCartItem of this.cartItems) {
      nextPriceValue += currentCartItem.quantity * currentCartItem.unitPrice;
      nextQuantityValue += currentCartItem.quantity;
    }

    this.totalQuantity.next(nextQuantityValue);
    this.totalPrice.next(nextPriceValue);

    this.logCartData(nextPriceValue, nextQuantityValue);
  }

  private logCartData(nextPriceValue: number, nextQuantityValue: number) {
    console.log('Contents of the cart');
    for (const item of this.cartItems) {
      const subTotalPrice = item.quantity * item.unitPrice;
      console.log(
        `name: ${item.name}, quantity=${item.quantity}, unitPrice=${item.unitPrice}, subTotalPrice=${subTotalPrice}`,
      );
    }
    console.log(
      `totalPrice: ${nextPriceValue.toFixed(
        2,
      )}, totalQuantity: ${nextQuantityValue}`,
    );
    console.log('----');
  }
}

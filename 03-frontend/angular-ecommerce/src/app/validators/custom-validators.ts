import { FormControl, ValidationErrors } from '@angular/forms';

export class CustomValidators {
  static notOnlyWhitespace(control: FormControl): null | ValidationErrors {
    if (control.value != null && control.value.trim().length === 0) {
      //invalid, return error object
      return {
        notOnlyWhitespace: true,
      };
    } else {
      // valid
      return null;
    }
  }
}

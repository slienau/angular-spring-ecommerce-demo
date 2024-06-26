import { Component, Inject, OnInit } from '@angular/core';
import { OKTA_AUTH, OktaAuthStateService } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';

@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css'],
})
export class LoginStatusComponent implements OnInit {
  isAuthenticated: boolean = false;
  userFullName: string = '';

  storage: Storage = sessionStorage;

  constructor(
    private oktaAuthService: OktaAuthStateService,
    @Inject(OKTA_AUTH) private oktaAuth: OktaAuth,
  ) {}

  ngOnInit(): void {
    this.oktaAuthService.authState$.subscribe((result) => {
      this.isAuthenticated = result.isAuthenticated!;
      this.getUserDetails();
    });
  }

  getUserDetails() {
    if (this.isAuthenticated) {
      return;
    }
    this.oktaAuth.getUser().then((result) => {
      if (result.name !== undefined) {
        this.userFullName = result.name as string;
      }
      if (result.email !== undefined) {
        this.storage.setItem('userEmail', JSON.stringify(result.email));
      } else {
        throw Error('No user E-Mail provided by okta!');
      }
    });
  }

  logout() {
    this.oktaAuth.signOut();
  }
}

import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ui';
  navbarOpen = false

  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }
}

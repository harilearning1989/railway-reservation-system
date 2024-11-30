import { TestBed } from '@angular/core/testing';

import { TrainDetailsService } from './train-details.service';

describe('TrainDetailsService', () => {
  let service: TrainDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrainDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

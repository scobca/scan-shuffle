import ApiResolver from '@/utils/ApiResolver.ts';

export class AuthResolver {
  private readonly apiResolver = new ApiResolver('api/v1/auth');

  public async getCsrf(): Promise<void> {
    await this.apiResolver.request<unknown>({
      url: 'csrf',
      method: 'GET',
    });
  }
}

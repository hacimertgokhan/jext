# Jext - A Lightweight Java HTTP Server Framework

Jext, basit bir HTTP sunucusu oluşturan ve dinamik web sayfaları sunan hafif bir Java framework'üdür. Bu framework, kullanıcıların yeni sayfalar oluşturmasına, CSS dosyalarını uygulamalarına ve dosya değişikliklerini takip etmelerine olanak tanır.

## Özellikler

- **Basit HTTP Sunucu**: Java ile hızlıca bir HTTP sunucusu kurun.
- **Dinamik Sayfa Yükleme**: Sayfa dizinleri ve `route.properties` dosyalarına dayalı olarak dinamik olarak sayfa yükleme.
- **Dosya İzleyici**: Sayfa dosyalarındaki değişiklikleri izler ve sunucu yollarını günceller.
- **Varsayılan CSS**: Basit bir CSS dosyasıyla sayfa stillerini uygulayın.

## Başlarken

Jext'i kullanmaya başlamak için aşağıdaki adımları izleyebilirsiniz:

### Prerequisites

- Java 17 veya daha yeni bir sürümüne sahip olmanız gerekmektedir.

### Projeyi Çalıştırma

1. Bu repoyu klonlayın:
   ```bash
   git clone https://github.com/hacimertgokhan/jext.git
   ```

2. Gerekli bağımlılıkları yükleyin (bu proje basit olduğu için ek bağımlılıklar yok).

3. `Main` sınıfını çalıştırarak sunucuyu başlatın:
   ```bash
   javac Main.java
   java Main
   ```

   Sunucu başlatıldıktan sonra, tarayıcınızda `http://localhost:5807` adresine giderek sayfanızı görüntüleyebilirsiniz.

### Sayfa Oluşturma

Yeni bir sayfa eklemek için aşağıdaki adımları izleyin:

1. `jext/pages/` dizininde bir klasör oluşturun, klasör isminin başına `_` koyarak sayfa ismini belirleyin (örneğin, `_home`).

2. Bu dizine `route.properties` ve `index.html` dosyalarını ekleyin. `route.properties` dosyasının içeriği şu şekilde olmalıdır:

   ```properties
   path=/home
   ```

3. `index.html` dosyasına basit bir içerik ekleyin:

   ```html
   <!DOCTYPE html>
   <html>
   <head>
       <meta charset="UTF-8">
       <title>Home</title>
       <link rel="stylesheet" href="/jext/style.css">
   </head>
   <body>
       <h1>Welcome to the Home Page!</h1>
   </body>
   </html>
   ```

   Sayfanız artık `/home` adresinde erişilebilir olacaktır.

### Dosya İzleyici

Jext, `jext/pages/` dizinindeki dosya değişikliklerini izleyerek, her değişiklik sonrasında rota dosyalarını yeniler. Bu özellik, özellikle sayfa eklerken veya değiştirirken faydalıdır.

## Kullanım

- **Yeni Sayfa Ekleme**: `jext/pages/` dizininde yeni bir klasör oluşturup, `route.properties` ve `index.html` dosyalarını ekleyin.
- **Stil Uygulama**: `jext/opt/style.css` dosyasını düzenleyerek tüm sayfalarınızda stil uygulayabilirsiniz.
- **Dinamik Sayfa Yükleme**: Sayfa dizinlerindeki dosya ve dizinler otomatik olarak izlenir, sayfa eklediğinizde veya düzenlediğinizde rotalar yeniden yüklenir.

## Katkı

Katkıda bulunmak isterseniz, lütfen pull request göndermek için repoyu fork edin. Herhangi bir hata raporu veya öneri için [issue tracker](https://github.com/hacimertgokhan/jext/issues) sayfasını kullanabilirsiniz.

## Lisans

Jext, MIT Lisansı altında lisanslanmıştır. Daha fazla bilgi için [LICENSE](LICENSE) dosyasına bakabilirsiniz.

## İletişim

- Yazar: [hacimertgokhan](https://github.com/hacimertgokhan)


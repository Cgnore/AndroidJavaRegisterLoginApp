# Android Firebase Login & Profile Management

Bu proje, Android platformunda **Firebase** servislerini kullanarak güvenli bir kullanıcı kayıt ve giriş sistemi oluşturmayı hedefler. Kullanıcılar sadece kayıt olmakla kalmaz, aynı zamanda profil bilgilerini güncelleyebilir ve verilerini tamamen silebilirler.

## 🚀 Özellikler

* **Kullanıcı Kaydı:** `MainActivity` üzerinden Firebase Auth ile yeni hesap oluşturma.
* **Kullanıcı Girişi:** `LoginActivity` ile e-posta ve şifre doğrulaması.
* **Firestore Entegrasyonu:** Kullanıcı bilgilerinin (Ad, E-posta, UID) NoSQL yapısında saklanması.
* **Profil Güncelleme:** Kayıtlı kullanıcı adını anlık olarak güncelleyebilme.
* **Veri Silme:** Kullanıcı verilerini Firestore üzerinden kalıcı olarak kaldırma.
* **Hata Yönetimi:** Kullanıcıya yönelik bilgilendirici Toast mesajları ve veri kontrolü.

## 🛠 Kullanılan Teknolojiler

* **Dil:** Java
* **Veritabanı:** Firebase Firestore
* **Kimlik Doğrulama:** Firebase Authentication
* **UI:** Material Design, Edge-to-Edge Support

## 📋 Kurulum ve Gereksinimler

Projeyi kendi bilgisayarınızda çalıştırmak için:

1.  Bu depoyu klonlayın: `git clone https://github.com/kullaniciadi/proje-adi.git`
2.  [Firebase Console](https://console.firebase.google.com/) üzerinden yeni bir proje oluşturun.
3.  **Authentication** (E-mail/Password) ve **Firestore** özelliklerini aktif edin.
4.  `google-services.json` dosyasını indirip projenizin `app/` klasörüne ekleyin.
5.  Android Studio ile projeyi derleyin ve çalıştırın.

## 📂 Kod Yapısı

* `MainActivity.java`: Kullanıcı kayıt işlemlerini ve Firestore'a ilk veri yazma işlemini yönetir.
* `LoginActivity.java`: Giriş yapma, veri çekme (Read), veri güncelleme (Update) ve silme (Delete) fonksiyonlarını içerir.
* `AndroidManifest.xml`: Uygulama izinleri ve aktivite tanımlamalarını barındırır.

## 🤝 Katkıda Bulunma

1. Bu projeyi fork edin.
2. Yeni bir özellik dalı (branch) oluşturun (`git checkout -b feature/yeniozellik`).
3. Değişikliklerinizi commit edin (`git commit -m 'Yeni özellik eklendi'`).
4. Dalınızı push edin (`git push origin feature/yeniozellik`).
5. Bir Pull Request açın.

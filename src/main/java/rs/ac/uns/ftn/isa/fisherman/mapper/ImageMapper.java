package rs.ac.uns.ftn.isa.fisherman.mapper;
import rs.ac.uns.ftn.isa.fisherman.dto.ImageDto;
import rs.ac.uns.ftn.isa.fisherman.model.Image;
import java.util.HashSet;
import java.util.Set;

public class ImageMapper {

    public Image imageDtoToImage(ImageDto image){
        return new Image(image.getId(),image.getUrl());
    }

    public ImageDto imageToImageDto(Image image) { return new ImageDto(image.getId(),image.getUrl());}

    public Set<Image> imageDtoSToImages(Set<ImageDto> imageDtoS){
        Set<Image> images = new HashSet<>();
        if(imageDtoS==null) System.out.println("slike su nulll");
        for(ImageDto imageDto: imageDtoS)
            images.add(imageDtoToImage(imageDto));
        return images;
    }
    public Set<ImageDto> imageToImageDtoS(Set<Image> images){
        Set<ImageDto> imagesDtoS = new HashSet<>();
        for(Image image: images)
            imagesDtoS.add(imageToImageDto(image));
        return imagesDtoS;
    }
}
